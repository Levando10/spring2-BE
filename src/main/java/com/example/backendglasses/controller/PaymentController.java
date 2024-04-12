package com.example.backendglasses.controller;

import com.example.backendglasses.config.ConfigVNP;
import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.ApiResponse;
import com.example.backendglasses.model.dto.PaymentDTO;
import com.example.backendglasses.model.dto.ProductDTO;
import com.example.backendglasses.model.dto.ShoppingCartItemDTO;
import com.example.backendglasses.service.impl.IProductService;
import com.example.backendglasses.service.impl.IShoppingCartItemService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import com.example.backendglasses.service.impl.IShoppingCartService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@CrossOrigin("*")
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private IShoppingCartItemService iShoppingCartItemService;

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private IProductService iProductService;

    @PostMapping("/createPay")
    private ResponseEntity<Object> payment(@RequestBody PaymentDTO paymentDTO) throws UnsupportedEncodingException {

        String amount = String.valueOf(paymentDTO.getTotalPayment() * 100);
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = ConfigVNP.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = ConfigVNP.vnp_TmnCode;
        String vnp_ReturnUrl = "http://localhost:3000/paymentOk/" + paymentDTO.getIdAccount();
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = ConfigVNP.hmacSHA512(ConfigVNP.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = ConfigVNP.vnp_PayUrl + "?" + queryUrl;



        User user = new User(paymentDTO.getIdAccount());
        ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
        List<ProductDTO> list = iProductService.detailCartIndividual(shoppingCart.getId());
        Product product;
        List<Product> productDTOListError = new ArrayList<>();
        for (ProductDTO temp : list){
            product = iProductService.findById(temp.getIdProduct());
            if (product == null){
                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
            } else {
                if (temp.getQuantity() > product.getQuantity()){
                    productDTOListError.add(iProductService.findById(temp.getIdProduct()));
                }

            }
        }


        if (productDTOListError.isEmpty()){
            ApiResponse<String> response = new ApiResponse<>();
            response.setDataRes(paymentUrl);
            response.setFlag("FOUND");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<List<Product>> response =  new ApiResponse<>();
            response.setDataRes(productDTOListError);
            response.setFlag("NOT");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }



    }

    @GetMapping("/payment_infor")
    public  ResponseEntity<Object> handlePaymentInfo(
            @RequestParam(value = "status") String status ,
            @RequestParam(value = "totalPrice",required = false)String totalPrice,
            @RequestParam(value = "idAccount",required = false)Long idAccount,
            @RequestParam(value = "fullName",required = false)String fullName,
            @RequestParam(value = "email",required = false)String email,
            @RequestParam(value = "address",required = false)String address,


            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String bankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String cardType,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "vnp_PayDate", required = false) String payDate,
            @RequestParam(value = "vnp_ResponseCode", required = false) String statusCode,
            @RequestParam(value = "vnp_TmnCode", required = false) String tmnCode,
            @RequestParam(value = "vnp_TransactionNo", required = false) String transactionNo,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String transactionStatus,
            @RequestParam(value = "vnp_TxnRef", required = false) String txnRef,
            @RequestParam(value = "vnp_SecureHash", required = false) String secureHash) {

        // Xử lý thông tin thanh toán ở đây
        System.out.println(status);
        if (!Objects.equals(status, "00")){
            return new ResponseEntity<>("NO", HttpStatus.OK);
        }

        List<Product> productDTO = new ArrayList<>();
        User user = new User(idAccount);
        ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
        List<ProductDTO> list = iProductService.detailCartIndividual(shoppingCart.getId());
        Product product;
       for (ProductDTO temp : list){
           product = iProductService.findById(temp.getIdProduct());
           if (product == null){
               return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
           } else {
               if (temp.getQuantity() > product.getQuantity()){
                   productDTO.add(iProductService.findById(temp.getIdProduct()));
               }
           }
       }

       if (productDTO.isEmpty()){
           for (ProductDTO temp : list){
               product = iProductService.findById(temp.getIdProduct());
               product.setQuantity(product.getQuantity() - temp.getQuantity());
               iProductService.save(product);
           }

       } else {
           ApiResponse<List<Product>> response =  new ApiResponse<>();
           response.setDataRes(productDTO);
           response.setFlag("NOT");
           return new ResponseEntity<>(response, HttpStatus.OK);
       }

        shoppingCart.setStatus("paid");
        shoppingCart.setAddressShip(address);

        LocalDateTime time = LocalDateTime.now();
        shoppingCart.setOrderDate(time);
        String subject = "[C0823G1-Glasses]-Mua kính thành công";
       Context context = new Context();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String timeConvert = time.format(formatter);

        context.setVariable("fullName", fullName);
        context.setVariable("totalPrice", totalPrice);
        context.setVariable("time", timeConvert);
        MimeMessage mimeMessage = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        iShoppingCartService.save(shoppingCart);
        try {
            helper.setTo(email);
            helper.setSubject(subject);
            String htmlContent = templateEngine.process("gmail", context);
            helper.setText(htmlContent, true);
            emailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
