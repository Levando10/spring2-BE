package com.example.backendglasses.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @NotBlank(message = "Mật Khẩu không được để rỗng")
    @Size(min = 6,max = 20,message = "Mật Khẩu độ dài từ 6-20 kí tự")
    String newPassword;
    @NotBlank(message = "Mật Khẩu không được để rỗng")
    @Size(min = 6,max = 20,message = "Mật Khẩu độ dài từ 6-20 kí tự")
    String currentPassword;
    @NotBlank(message = "Mật Khẩu không được để rỗng")
    @Size(min = 6,max = 20,message = "Mật Khẩu độ dài từ 6-20 kí tự")
    String confirmPassword;
    Long idAccount;
}
