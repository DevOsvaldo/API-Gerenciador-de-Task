package com.api_todolist.osvaldo.ToDoList.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.api_todolist.osvaldo.ToDoList.user.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {


        var servletPath = request.getServletPath();
        if(servletPath.startsWith("/tasks/")){

            //Pegar a autenticação(usuário e senha)
            var authorization = request.getHeader("Authorization");

            //metodo para remover a palavra Basic que vem antes da authorization,
            // e o trim remove todo espaço em branco
            var authEncode = authorization.substring("Basic".length()).trim();

            byte[] authDecode = Base64.getDecoder().decode(authEncode);
            var authString = new String(authDecode);

            String[] credentials = authString.split(":");
            String username = credentials[0];
            String password = credentials[1];

            //validade o usuário
            var user = this.userRepository.findByUsername(username);
            if(user == null){
                response.sendError(401);
            } else{
                //validar senha
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if(passwordVerify.verified){
                    //segue viagem
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request,response);
                } else {
                    response.sendError(401);
                }

            }

        } else {
            filterChain.doFilter(request,response);
        }
        }

        }


