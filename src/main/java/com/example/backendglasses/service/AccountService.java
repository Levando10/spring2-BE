package com.example.backendglasses.service;

import com.example.backendglasses.config.JwtTokenUtil;
import com.example.backendglasses.model.Role;
import com.example.backendglasses.model.User;
import com.example.backendglasses.repository.AccountRepository;
import com.example.backendglasses.service.impl.IAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class AccountService implements IAccountService {
    @Autowired
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final PasswordEncoder passwordEncoder;


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public  Optional<User> findAccountByEmail(String email) {

        return accountRepository.findByEmail(email);
    }


    @Override
    public Optional<User>  findAccountByAccountName(String accountName) {
        return accountRepository.findByNameAccount(accountName);
    }

    @Override
    public User registerAccount(User user) throws Exception  {
     return    accountRepository.save(user);
    }

    @Override
    public  Optional<User> findAccountByPhone(String phoneNumber) {
        return accountRepository.findByPhoneNumber(phoneNumber);
    }

    @Override
    public String login(String nameAccount, String passWord) throws DataFormatException {
        Optional<User>  optionalUser = accountRepository.findByNameAccount(nameAccount);
        if (optionalUser.isEmpty()){
            throw new DataFormatException("Sai tai khoan hoac mat khau ");
        }
        User existingUser = optionalUser.get();

        // chua dang nhap google
        if (!passwordEncoder.matches(passWord, existingUser.getPassword())){
            throw new BadCredentialsException("sai toan khoan hoac mat khau");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                nameAccount, passWord,existingUser.getAuthorities()
        );
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(optionalUser.get());
    }

    @Override
    public User findAccountByAccountIdAccount(Long idAccount) {
        return accountRepository.findById(idAccount).get();
    }

    @Override
    public void save(User user) {
        accountRepository.save(user);
    }

    @Override
    public User findAccountByRole(Role role) {
        return accountRepository.findUserByRole(role);
    }

    @Override
    public List<User> findAll() {
        return accountRepository.findAllAccountUser();
    }
}
