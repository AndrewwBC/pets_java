package com.example.pets4ever.user.validations;

import com.example.pets4ever.infra.exceptions.user.dto.ErrorListDTO;
import com.example.pets4ever.infra.exceptions.user.validation.SigninException;
import com.example.pets4ever.infra.exceptions.user.validation.UserValidationsException;
import com.example.pets4ever.user.User;
import com.example.pets4ever.user.UserRepository;
import com.example.pets4ever.user.dtos.PatchPasswordDTO;
import com.example.pets4ever.user.dtos.PatchProfileDTO;
import com.example.pets4ever.user.dtos.SignUpDTO;
import com.example.pets4ever.user.dtos.SignInDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class UserValidations implements UserValidate{

    @Autowired
    UserRepository userRepository;
    @Override
    public void signUpValidate(SignUpDTO signupDTO) {
        boolean usernameAlreadyInUse = existsByUsername(signupDTO.getUsername());
        boolean emailAlreadyInUse = existsByEmail(signupDTO.getEmail());

        List<ErrorListDTO> errorList = new ArrayList<>();

        if(usernameAlreadyInUse){
            errorList.add(new ErrorListDTO("username", "Nome de usuário já cadastrado."));
        }
        if(emailAlreadyInUse){
            errorList.add(new ErrorListDTO("email", "Email já cadastrado."));
        }

        if(!errorList.isEmpty()){
            throw new UserValidationsException(errorList);
        }
    }

    @Override
    public void signInValidate(SignInDTO signInDTO) {

        System.out.println("antes");
        User user = this.userRepository.findByEmail(signInDTO.email()).orElseThrow(()
                -> new SigninException("Email ou senha incorretos."));

        System.out.println(user);

        String cryptedPassword = user.getPassword();
        String loginPassword = signInDTO.password();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if(!encoder.matches(loginPassword, cryptedPassword)) {
            throw new SigninException("Email ou senha incorretos.");
        }
    }

    @Override
    public void patchProfileValidate(PatchProfileDTO patchProfileDTO) {
        List<ErrorListDTO> errorList = new ArrayList<>();

        if(existsByUsername(patchProfileDTO.username())){
            errorList.add(new ErrorListDTO("username", "Nome de usuário já cadastrado"));
        }

        if(!errorList.isEmpty()){
            throw new UserValidationsException(errorList);
        }
    }

    @Override
    public void patchProfileImage(MultipartFile file) {
        List<ErrorListDTO> errorListDTOList = new ArrayList<>();

        if(file == null){
            errorListDTOList.add(new ErrorListDTO("image", "O campo imagem é obrigatório!"));
        }

        if(!errorListDTOList.isEmpty()) {
            throw new UserValidationsException(errorListDTOList);
        }
    }

    public void patchPassword(String username, PatchPasswordDTO patchPasswordDTO){

        List<ErrorListDTO> errorList = new ArrayList<>();

        if(patchPasswordDTO.actualPassword().equals(patchPasswordDTO.newPassword())) {
            errorList.add(new ErrorListDTO("newPassword", "A nova senha não pode ser igual a senha atual."));
            throw new UserValidationsException(errorList);
        }

        User user = this.userRepository.findByUsername(username).orElseThrow(() ->
                new NoSuchElementException("Usuário não encontrado"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


        String cryptedPassword = user.getPassword();
        String actualPassword = patchPasswordDTO.actualPassword();

        if(!encoder.matches(actualPassword, cryptedPassword)) {
             errorList.add(new ErrorListDTO("actualPassword", "Senha incorreta"));
            throw new UserValidationsException(errorList);
        }
    }

    private boolean existsByFullname(String fullname){
        return userRepository.existsByFullname(fullname);
    }

    private boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

    private boolean existsByEmail(String email) { return userRepository.existsByEmail(email); }
}
