package com.example.backendglasses.repository;

import com.example.backendglasses.model.Product;
import com.example.backendglasses.model.dto.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    Page<Product> findAllByQuantityGreaterThanAndIsDeletedFalse(Pageable pageable,Integer integer);
    @Query(value = "select product.id as idProduct, product.name as nameProduct, product.price as price,\n" +
            "            manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct, category.name as nameCategory\n" +
            "            from product\n" +
            "            join image_product on image_product.product_image = product.id\n" +
            "            join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "            join category on category.id = product.category_id\n" +
            "where product.quantity >0\n"+
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> findAllProductDTO(Pageable pageable);

    @Query(value = "select product.name as nameProduct, product.price as price, max(image_product.url_image) as imageMax ," +
            " manufacturer.name as nameFac, product.imageProducts as image, product.id as idProduct, product.description as description, category.name as nameCategory\n" +
            "from product\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            " JOIN image_product ON image_product.product_image = product.id\n" +
            "join category on category.id = product.category_id\n" +
            "where product.id = :id" +
            "group by product.id\n",nativeQuery = true)
    ProductDTO findProductById(Long id);

    @Query(value = "  SELECT product_id as idProduct,\n" +
            "       (SELECT COUNT(*)\n" +
            "        FROM shopping_cart_item sci\n" +
            "        WHERE sci.product_id = product.id AND sci.cart_id = :idCart) as quantity,\n" +
            "       product.name as nameProduct, \n" +
            "       product.price as price,\n" +
            "       max(image_product.url_image) as imageMax\n" +
            "FROM shopping_cart_item\n" +
            "JOIN product ON product.id = shopping_cart_item.product_id\n" +
            " JOIN image_product ON image_product.product_image = product.id\n" +
            "WHERE shopping_cart_item.cart_id = :idCart\n" +
            "GROUP BY product_id\n" +
            "ORDER BY quantity DESC;"
            ,nativeQuery = true)
    List<ProductDTO> detailCartIndividual(Long idCart);


    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where category.name like %:searchCan% and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamCan(Pageable pageable,String searchCan);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac,  max(image_product.url_image) as imageMax, product.id as idProduct ,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where category.name like %:searchMat% and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamMat(Pageable pageable, String searchMat);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac,max(image_product.url_image) as imageMax, product.id as idProduct, category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchMat% or category.name like %:searchCan%) and manufacturer.name like %:searchGucci% and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamCanAndMatAndGucci(Pageable pageable, String searchCan, String searchMat, String searchGucci);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax , product.id as idProduct, category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchMat% or category.name like %:searchCan%) and manufacturer.name like %:searchDior% and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamCanAndMatAndDior(Pageable pageable, String searchCan, String searchMat, String searchDior);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct, category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchCan%) and manufacturer.name like %:searchGucci% and product.quantity > 0 \n " +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamCanAndGuuci(Pageable pageable, String searchCan, String searchGucci);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac,max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchCan%) and manufacturer.name like %:searchDior% and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamCanAndDior(Pageable pageable, String searchCan, String searchDior);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac,max(image_product.url_image) as imageMax , product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchCan%) and (manufacturer.name like %:searchDior%  or manufacturer.name like %:searchGucci%) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamCanAndDiorAndGuuci(Pageable pageable, String searchCan, String searchGucci, String searchDior);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct, category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchMat%) and (manufacturer.name like %:searchGucci% ) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamMatAndGuuci(Pageable pageable, String searchMat, String searchGucci);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchMat%) and (manufacturer.name like %:searchDior% ) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamMatAndDior(Pageable pageable, String searchMat, String searchDior);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where (category.name like %:searchMat%) and (manufacturer.name like %:searchDior%  or manufacturer.name like %:searchGucci%  ) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamMatAndDiorAndGuuci(Pageable pageable, String searchMat, String searchGucci, String searchDior);

    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac, max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where  (manufacturer.name like %:searchGucci% ) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamGucci(Pageable pageable, String searchGucci);
    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac,max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where  (manufacturer.name like %:searchDior% ) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamDior(Pageable pageable, String searchDior);
    @Query(value = "select product.name as nameProduct, product.price as price," +
            " manufacturer.name as nameFac,max(image_product.url_image) as imageMax, product.id as idProduct,category.name as nameCategory\n" +
            "from product\n" +
            "join image_product on image_product.product_image = product.id\n" +
            "join manufacturer on manufacturer.id = product.manufacturer_id\n" +
            "join category on category.id = product.category_id\n" +
            "where  (manufacturer.name like %:searchDior% or manufacturer.name like %:searchGucci%) and product.quantity > 0\n" +
            "            group by product.id\n" +
            "order by product.price desc",nativeQuery = true)
    Page<ProductDTO> searchWithParamGucciAndDior(Pageable pageable, String searchGucci, String searchDior);
}
