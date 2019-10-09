package osa.projekat.security;

import java.lang.annotation.Documented;


import org.springframework.security.core.annotation.AuthenticationPrincipal;


@Documented
@AuthenticationPrincipal
public @interface CurrentUser {

}
