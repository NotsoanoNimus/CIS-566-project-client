package xyz.xmit.silverclient.models;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataField
{
    public boolean hidden() default false;

    public int lengthLimit() default 2^16;
}
