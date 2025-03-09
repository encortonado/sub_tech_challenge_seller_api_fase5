package br.com.fiap.tech.sub_tech_challenge_seller_api_fase5.infrastructure.helpers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CpfMaskSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String cpf, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (cpf != null && cpf.length() == 11) {
            String maskedCpf = cpf.substring(0, 3) + ".***.***-" + cpf.substring(9);
            jsonGenerator.writeString(maskedCpf);
        } else {
            jsonGenerator.writeString(cpf); // Se não tiver o formato esperado, retorna como está
        }
    }
}
