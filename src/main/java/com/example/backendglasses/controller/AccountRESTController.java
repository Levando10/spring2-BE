package com.example.backendglasses.controller;
import com.example.backendglasses.model.Role;
import com.example.backendglasses.model.ShoppingCart;
import com.example.backendglasses.model.User;
import com.example.backendglasses.model.dto.AccountDTO;
import com.example.backendglasses.model.dto.ApiResponse;
import com.example.backendglasses.model.dto.ChangePasswordDTO;
import com.example.backendglasses.service.impl.IAccountService;
import com.example.backendglasses.service.impl.IShoppingCartService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("account")
public class AccountRESTController {
    @Autowired
    private IAccountService iAccountService;
    @Autowired
    private IShoppingCartService iShoppingCartService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/information/{idAccount}")
    public ResponseEntity<Object> informationUser(@PathVariable Long idAccount){
        User user = iAccountService.findAccountByAccountIdAccount(idAccount);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }



    @PostMapping("/register")
    public ResponseEntity<Object> createAccount(@RequestBody @Valid AccountDTO accountDTO,
                                                BindingResult bindingResult) throws Exception {
        Map<String, String> listError = new HashMap<>();
        if (bindingResult.hasFieldErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Optional<User> userEmail = iAccountService.findAccountByEmail(accountDTO.getEmail());
            Optional<User> userPhone = iAccountService.findAccountByPhone(accountDTO.getPhoneNumber());
            Optional<User> userAccount = iAccountService.findAccountByAccountName(accountDTO.getNameAccount());
            System.out.println(userAccount.isPresent() + " " + userEmail.isPresent() +"  " + userPhone.isPresent());

            if (userEmail.isPresent()) {
                System.out.println(iAccountService.findAccountByEmail(accountDTO.getEmail()));
                listError.put("email", "Email Đã Tồn Tại");
            }
            if (userPhone.isPresent()) {
                listError.put("phoneNumber", "Số Điện Thoại Đã Tồn Tại");
            }
            if (userAccount.isPresent()) {
                listError.put("accountName", "Tài Khoản Đã Tồn Tại");
            }
            if (!listError.isEmpty()) {
                return new ResponseEntity<>(listError, HttpStatus.BAD_REQUEST);
            }

            String encode = passwordEncoder.encode(accountDTO.getPassword());

            User user = new User();
            BeanUtils.copyProperties(accountDTO, user);
            user.setPassword(encode);
            Role role = new Role(3L);
            user.setRole(role);
           User userTemp = iAccountService.registerAccount(user);
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setUser(userTemp);
            shoppingCart.setStatus("unpaid");
            iShoppingCartService.save(shoppingCart);

            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PostMapping("/changPassword")
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO,
                                                BindingResult bindingResult) throws Exception {
        Map<String, String> listError = new HashMap<>();
        if (bindingResult.hasFieldErrors()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = iAccountService.findAccountByAccountIdAccount(changePasswordDTO.getIdAccount());

        ApiResponse<User> apiResponse = new ApiResponse<>();


        if (!passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword()) ){
            listError.put("currentPassword", "Mật khẩu đã nhập sai");
        }
        if (!listError.isEmpty()) {
            return new ResponseEntity<>(listError, HttpStatus.BAD_REQUEST);
        }
        String encode = passwordEncoder.encode(changePasswordDTO.getConfirmPassword());
        user.setPassword(encode);
            iAccountService.save(user);
        User userTemp = iAccountService.findAccountByAccountIdAccount(changePasswordDTO.getIdAccount());
        String token = iAccountService.login(userTemp.getNameAccount(), changePasswordDTO.getConfirmPassword());
        apiResponse.setToken(token);
        apiResponse.setDataRes(user);

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginAccount(HttpServletResponse response, @RequestBody AccountDTO accountDTO){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        try{
            String token = iAccountService.login(accountDTO.getNameAccount(), accountDTO.getPassword());
            User user = iAccountService.findAccountByAccountName(accountDTO.getNameAccount()).get();
            apiResponse.setToken(token);
            apiResponse.setDataRes(user);
//            Cookie jwtCookie = new Cookie("JWT",token);
//            jwtCookie.setHttpOnly(true);
//            jwtCookie.setSecure(true);
//            jwtCookie.setPath("/");
//            jwtCookie.setMaxAge(7 * 24 * 60 * 60);
//            response.addCookie(jwtCookie);

            return ResponseEntity.ok(apiResponse);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }



}
