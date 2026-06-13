package com.mikuyun.admin.annotation;

import java.lang.annotation.*;

/**
 * <p>不需要校验token</p>
 *
 * @author mikuyun
 * @since 2026/4/22 20:29
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenIgnore {
}
