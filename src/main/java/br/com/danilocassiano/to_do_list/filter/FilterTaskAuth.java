package br.com.danilocassiano.to_do_list.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.danilocassiano.to_do_list.user.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();

        if (servletPath.equals("/tasks/")) {

            // Obter a autenticação (Usuário e Senha)
            var authorization = request.getHeader("Authorization");

            if (authorization != null && authorization.startsWith("Basic")) {
                var authEncoded = authorization.substring("Basic".length()).trim();

                byte[] authDecode = Base64.getDecoder().decode(authEncoded);

                var authString = new String(authDecode);

                String[] credentials = authString.split(":");
                String username = credentials[0];
                String password = credentials[1];

                var user = this.userRepository.findByUsername(username);

                if (user == null) {
                    response.sendError(401);
                } else {
                    // Validando Senha.
                    var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                    if (passwordVerify.verified) {
                        filterChain.doFilter(request, response);
                    } else {
                        response.sendError(401);
                    }
                }
            } else {
                response.sendError(401);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
