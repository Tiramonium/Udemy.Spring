package com.udemy.spring.constraints;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.udemy.spring.exceptions.ValidationExceptionField;

public class CpfCnpjValidator implements ConstraintValidator<ValidateCpfCnpj, Object> {
    private String customMessage;

    @Override
    public void initialize(ValidateCpfCnpj constraintAnnotation) {
        this.customMessage = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        Field[] propriedades = obj.getClass().getFields();
        List<ValidationExceptionField> lista = new ArrayList<ValidationExceptionField>();
        Boolean propriedadeEncontrada = false;

        for (Field propriedade : propriedades)
        {
            if (propriedade.isAnnotationPresent(CpfCnpj.class))
            {
                try
                {
                    Object valor = propriedade.get(obj);
                    propriedadeEncontrada = true;

                    if (valor == null)
                    {
                        return true;
                    }
                    else if (!isValidCPF(valor) && !isValidCNPJ(valor))
                    {
                        String message = context.getDefaultConstraintMessageTemplate();
                        message = !this.customMessage.equals(message) ? this.customMessage : message;
                        lista.add(new ValidationExceptionField(propriedade.getName(), message));
                    }
                }
                catch (IllegalArgumentException | IllegalAccessException ex)
                {
                    ex.printStackTrace();
                }
            }
            else if (propriedade == propriedades[propriedades.length - 1] && !propriedadeEncontrada)
            {
                context.buildConstraintViolationWithTemplate("Nenhuma propriedade foi marcada com a anotação @CpfCnpj").addConstraintViolation();
                return false;
            }
        }

        if (!lista.isEmpty())
        {
            context.disableDefaultConstraintViolation();

            for (ValidationExceptionField ex : lista)
            {
                context.buildConstraintViolationWithTemplate(ex.message).addPropertyNode(ex.fieldName).addConstraintViolation();
            }
        }

        return lista.isEmpty();
    }

    private boolean isValidCPF(Object valor) {
        if (String.valueOf(valor).isEmpty())
        {
            return false;
        }

        String valorStr = valor.toString();

        if (valorStr.replaceAll("\\D", "").length() != 11 || valorStr.replaceAll(valorStr.substring(0, 1), "").length() == 0)
        {
            return false;
        }

        String cpf = valorStr.replaceAll("\\D", "");
        int multiplicador = 10;
        int soma = 0;

        for (int i = 0; i <= 8; i++)
        {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * multiplicador;
            multiplicador--;
        }

        int resto = (soma * 10) % 11;
        resto = resto == 10 ? 0 : resto;

        if (resto != Integer.parseInt(String.valueOf(cpf.charAt(9))))
        {
            return false;
        }

        multiplicador = 11;
        soma = 0;

        for (int i = 0; i <= 9; i++)
        {
            soma += Integer.parseInt(String.valueOf(cpf.charAt(i))) * multiplicador;
            multiplicador--;
        }

        resto = (soma * 10) % 11;
        resto = resto == 10 ? 0 : resto;

        if (resto != Integer.parseInt(String.valueOf(cpf.charAt(10))))
        {
            return false;
        }

        return true;
    }

    private boolean isValidCNPJ(Object valor) {
        if (String.valueOf(valor).isEmpty() || valor.toString().replaceAll("\\D", "").length() != 14)
        {
            return false;
        }

        String cnpj = valor.toString().replaceAll("\\D", "");
        int multiplicador = 5;
        int soma = 0;

        for (int i = 0; i <= 11; i++)
        {
            soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * multiplicador;
            multiplicador = multiplicador >= 3 ? multiplicador - 1 : 9;
        }

        int resto = soma % 11;
        resto = resto <= 1 ? 0 : 11 - resto;

        if (resto != Integer.parseInt(String.valueOf(cnpj.charAt(12))))
        {
            return false;
        }

        multiplicador = 6;
        soma = 0;

        for (int i = 0; i <= 12; i++)
        {
            soma += Integer.parseInt(String.valueOf(cnpj.charAt(i))) * multiplicador;
            multiplicador = multiplicador >= 3 ? multiplicador - 1 : 9;
        }

        resto = soma % 11;
        resto = resto <= 1 ? 0 : 11 - resto;

        if (resto != Integer.parseInt(String.valueOf(cnpj.charAt(13))))
        {
            return false;
        }

        return true;
    }
}