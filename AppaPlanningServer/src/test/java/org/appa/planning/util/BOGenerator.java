package org.appa.planning.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.appa.planning.bo.Projet;
import org.appa.planning.bo.Utilisateur;

public class BOGenerator {

	private static Logger logger = Logger.getLogger(BOGenerator.class);

	public static <T> T generate(Class<? extends T> classe) throws Exception{

		//création d'une nouvelle instance
		T object = classe.newInstance();

		//pour chaque attribut
		for(Field field : classe.getDeclaredFields()){

			//pas de traitement si l'attribut est final ou static
			if(Modifier.isFinal(field.getModifiers()) || Modifier.isStatic(field.getModifiers())){
				continue;
			}

			if(field.getAnnotation(NotNull.class) == null){
				continue;
			}

			//récupération du setter associé
			Method setterMethod = null;
			try{
				setterMethod = classe.getMethod("set" + StringUtils.capitalize(field.getName()), field.getType());
			}catch (NoSuchMethodException e) {
				logger.warn("no setter for field " + field.getName());
				continue;
			}

			//génération d'une valeur aléatoire en fonction du type
			Class fieldClass = field.getType();
			Object setterValue = null;

			if(fieldClass.getSimpleName().equals("String")){
				setterValue = generateStringValue(field);
			}

			if(fieldClass.getSimpleName().equals("Integer") || fieldClass.getSimpleName().equals("int")){
				setterValue = generateIntegerValue(field);
			}

			if(fieldClass.getSimpleName().equals("long") || fieldClass.getSimpleName().equals("Long") ){
				setterValue = generateLongValue(field);
			}

			setterMethod.invoke(object,setterValue);

		}


		return object;
	}

	private static String generateStringValue(Field field){
		//récupération de la size min / max via annotation éventuelle
		Size annotation = field.getAnnotation(Size.class);
		if(annotation != null){
			return BasicDataGenerator.generateRandomString(annotation.max());
		}else{
			//si pas d'annotation, taille fixe de 1
			return BasicDataGenerator.generateRandomString(1);
		}
	}

	private static Integer generateIntegerValue(Field field){
		//récupération de la size min / max via annotation éventuelle
		Max annotation = field.getAnnotation(Max.class);
		if(annotation != null){
			return BasicDataGenerator.generateRandomInt(new Long(annotation.value()).intValue());
		}else{
			//si pas d'annotation, taille par défaut
			return BasicDataGenerator.generateRandomInt(Integer.MAX_VALUE - 1);
		}
	}

	private static Long generateLongValue(Field field){

		//id autogénéré
		GeneratedValue annotation2 = field.getAnnotation(GeneratedValue.class);
		if(annotation2 != null){
			if(annotation2.strategy() == GenerationType.AUTO){
				return null;
			}
		}

		//récupération de la size min / max via annotation éventuelle
		Max annotation = field.getAnnotation(Max.class);
		if(annotation != null){
			return BasicDataGenerator.generateRandomLong(annotation.value());
		}else{
			//si pas d'annotation, taille par défaut
			return BasicDataGenerator.generateRandomLong(Long.MAX_VALUE - 1);
		}
	}

	public static void main(String[] args) throws Exception {

		for (int i = 0; i < 10; i++) {
			Projet projet = BOGenerator.generate(Projet.class);
			System.out.println(projet);
		}

		for (int i = 0; i < 10; i++) {
			Utilisateur u = BOGenerator.generate(Utilisateur.class);
			System.out.println(u);
		}
	}
}
