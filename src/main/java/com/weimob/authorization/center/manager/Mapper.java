
 /*
 * ArrrowCtrl Inc., All rights reserved. 2017. 
 * Oceanware component, Oceanware is a brand owned by ArrowCtrl. 
 * Any use of this component should get permission from ArrowCtrl.
 */

package com.weimob.authorization.center.manager;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, ANNOTATION_TYPE })
/**
 * Description of Mapper
 *
 * @author charles.chen
 * @created on 2017年2月28日
 * 
 * @version $Id:$
 */
public @interface Mapper{

}
