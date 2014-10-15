package com.resolve.qa.framework.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

public class TransformJson
{

    /**
     * @param args
     * @throws IOException 
     * @throws JsonProcessingException 
     */
    public static void main(String[] args) throws JsonProcessingException, IOException
    {
        migrate("C:\\Users\\xia.hua\\Downloads\\AjaxUpdateTagValidation.json");

    }
    
    public static void migrate(String file) throws JsonProcessingException, IOException {
        ObjectMapper tempMapper = new ObjectMapper();
        ObjectNode rawNode = (ObjectNode)tempMapper.readTree(new File(file));
        if(rawNode.get("tests")!=null) {
            ArrayNode arr = (ArrayNode)rawNode.get("tests");
            for(int i=0;i<arr.size();i++) {
                ObjectNode request = (ObjectNode)arr.get(i);
                if(request.get("jsonPayload")!=null && request.get("jsonPayload").get("validationReplaceJsonField")!=null) {
                    ArrayNode validations = (ArrayNode)request.get("jsonPayload").get("validationReplaceJsonField");
                    
                    for(int j=0;j<validations.size();j++) {
                        ObjectNode validation = (ObjectNode)validations.get(j);
                        if(validation.get("invalidCase")!=null)
                        {
                            ObjectNode negativeCase = (ObjectNode)tempMapper.readTree("{}");
                            negativeCase.put("invalidCase",validation.get("invalidCase"));
                            validation.remove("invalidCase");
                            validation.put("negativeCase", negativeCase);
                            
                        }
                    }
                    PrintWriter out = new PrintWriter(file);
                    out.println(rawNode.toString());
                    out.close();
         /*           byte[] bytes = rawNode.toString().getBytes();
                    try (OutputStream out = new FileOutputStream(file)) {
                        out.write(bytes);
                        out.flush();
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    
               //     System.out.print(rawNode.toString());
                }
            }
        }
    }

}
