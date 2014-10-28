package com.resolve.qa.framework;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.net.URLEncoder;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.resolve.qa.framework.common.*;
import com.resolve.qa.framework.util.JsonUtil;
import com.resolve.qa.framework.util.RsqaWebClient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ControllerTest
{
    String name;
    String description;
    String path;
    @JsonProperty(required = true)
    HTTP_METHODS method;
    @JsonProperty(required = true)
    MEDIA_TYPE requestType;
    @JsonProperty(required = true)
    MEDIA_TYPE responseType;

    List<ConditionCheck> exeCondition = new ArrayList<ConditionCheck>();
    
    List<LocalValue> queryParams = new ArrayList<LocalValue>();
    List<ValidationLocalValue> validationQueryParams = new ArrayList<ValidationLocalValue>();

    List<LocalValue> requestForm = new ArrayList<LocalValue>();
    List<ValidationLocalValue> validationRequestForm = new ArrayList<ValidationLocalValue>();

    JsonCreator jsonPayload;

    ExpectResponse handleResponse;
    List<TestOp> testOps = new ArrayList<TestOp>();
    List<EndConditon> endConditons = new ArrayList<EndConditon>();

    String altUser;
    String altPass;
    
    HashMap<String, Progress> validationProgress = new HashMap<String, Progress>();
    Progress cur = null;

    static String cacheFilter=null;
    
    
    public ControllerTest(String name, String description, String path, HTTP_METHODS method, MEDIA_TYPE responseType, MEDIA_TYPE requestType, List<LocalValue> queryParams, List<LocalValue> requestForm, List<ValidationLocalValue> validationQueryParams, List<ValidationLocalValue> validationRequestForm, JsonCreator jsonPayload, ExpectResponse handleResponse, List<TestOp> testOps, List<EndConditon> endConditons)
    {
        super();
        this.name = name;
        this.description = description;
        this.path = path;
        this.method = method;
        this.responseType = responseType;
        this.requestType = requestType;
        this.queryParams = queryParams;
        this.requestForm = requestForm;
        this.validationQueryParams = validationQueryParams;
        this.validationRequestForm = validationRequestForm;
        this.jsonPayload = jsonPayload;
        this.handleResponse = handleResponse;
        this.testOps = testOps;
        this.endConditons = endConditons;
    }

    public ControllerTest()
    {
        super();
        // TODO Auto-generated constructor stub
    }

    public String getName()
    {
        return name;
    }

    
    public List<ConditionCheck> getExeCondition()
    {
        return exeCondition;
    }

    public void setExeCondition(List<ConditionCheck> exeCondition)
    {
        this.exeCondition = exeCondition;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public HTTP_METHODS getMethod()
    {
        return method;
    }

    public void setMethod(HTTP_METHODS method)
    {
        this.method = method;
    }

    public MEDIA_TYPE getRequestType()
    {
        return requestType;
    }

    public void setRequestType(MEDIA_TYPE requestType)
    {
        this.requestType = requestType;
    }

    public MEDIA_TYPE getResponseType()
    {
        return responseType;
    }

    public void setResponseType(MEDIA_TYPE responseType)
    {
        this.responseType = responseType;
    }

    public List<LocalValue> getQueryParams()
    {
        return queryParams;
    }

    public void setQueryParams(List<LocalValue> queryParams)
    {
        this.queryParams = queryParams;
    }

    public List<LocalValue> getRequestForm()
    {
        return requestForm;
    }

    public void setRequestForm(List<LocalValue> requestForm)
    {
        this.requestForm = requestForm;
    }

    public List<ValidationLocalValue> getValidationQueryParams()
    {
        return validationQueryParams;
    }

    public void setValidationQueryParams(List<ValidationLocalValue> validationQueryParams)
    {
        this.validationQueryParams = validationQueryParams;
    }

    public List<ValidationLocalValue> getValidationRequestForm()
    {
        return validationRequestForm;
    }

    public void setValidationRequestForm(List<ValidationLocalValue> validationRequestForm)
    {
        this.validationRequestForm = validationRequestForm;
    }

    public JsonCreator getJsonPayload()
    {
        return jsonPayload;
    }

    public void setJsonPayload(JsonCreator jsonPayload)
    {
        this.jsonPayload = jsonPayload;
    }

    public ExpectResponse getHandleResponse()
    {
        return handleResponse;
    }

    public void setHandleResponse(ExpectResponse handleResponse)
    {
        this.handleResponse = handleResponse;
    }

    public List<TestOp> getTestOps()
    {
        return testOps;
    }

    public void setTestOps(List<TestOp> testOps)
    {
        this.testOps = testOps;
    }

    public List<EndConditon> getEndConditons()
    {
        return endConditons;
    }

    public void setEndConditons(List<EndConditon> endConditons)
    {
        this.endConditons = endConditons;
    }

    public String getAltUser()
    {
        return altUser;
    }

    public void setAltUser(String altUser)
    {
        this.altUser = altUser;
    }

    public String getAltPass()
    {
        return altPass;
    }

    public void setAltPass(String altPass)
    {
        this.altPass = altPass;
    }

    public boolean execute(int testNumber, String baseURL, RsqaWebClient wc, Map<String, Object> testData) throws Exception
    {

        Log.log("TEST " + testNumber + ": " + description);
        wc.setReturnType(responseType.toString());
        wc.setRequestType(requestType.toString());
        Map<String, String> params = new HashMap<String, String>();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj = null;
        String response = null;
        boolean end = true;
        Progress temp = null;
        boolean completed = true;

        ExpectResponse exeHandleResponse = null;
        List<TestOp> exeTestOps = null;

        if (!queryParams.isEmpty())
        {
            for (LocalValue queryParam : queryParams)
            {

                String raw = queryParam.getRealValue(testData);
                if(queryParam.getKey().endsWith("filter") && raw.endsWith("filter")){
                    params.put(queryParam.getKey(), URLEncoder.encode(cacheFilter, "UTF-8").replace("+", "%20"));
                }
                else {
                    if(queryParam.getRealValue(testData)!=null)params.put(queryParam.getKey(), URLEncoder.encode(queryParam.getRealValue(testData), "UTF-8").replace("+", "%20"));
                    else params.put(queryParam.getKey(), null);
                }
            }
        }
        if (!validationQueryParams.isEmpty())
        {
            for (ValidationLocalValue validationQueryParam : validationQueryParams)
            {
                String key = validationQueryParam.getKey();
                int index;
                int size = 0;
                int validSize = 0;
                int invalidSize = 0;
                if (validationQueryParam.getValidCase() != null && validationQueryParam.getValidCase().size() != 0)
                {
                    validSize = validationQueryParam.getValidCase().size();
                }
                if (validationQueryParam.getNegativeCase() != null && validationQueryParam.getNegativeCase().getInvalidCase().size() != 0)
                {
                    invalidSize = validationQueryParam.getNegativeCase().getInvalidCase().size();
                }

                size = validSize + invalidSize;
                if (!validationProgress.containsKey(key))
                {
                    if (size != 0)
                    {
                        if (validSize != 0)
                            temp = new Progress(0, size, true, temp, null);
                        else
                            temp = new Progress(-1, size, false, temp, null);
                        if (temp.getPre() != null) temp.getPre().setNext(temp);
                        validationProgress.put(key, temp);
                        if (cur == null) cur = temp;
                    }
                }
                index = validationProgress.get(key).getCur();
                
                if (index >= validSize)
                {
                    index = index - validSize;

                    params.put(validationQueryParam.getKey(), URLEncoder.encode(validationQueryParam.getNegativeCase().getInvalidCase().get(index).getValue(), "UTF-8").replace("+", "%20"));

                    exeHandleResponse = new ExpectResponse(validationQueryParam.getNegativeCase().getHandleResponse());
                    exeTestOps = new ArrayList<TestOp>();
                    if(validationQueryParam.getNegativeCase().getTestOps()!=null)exeTestOps.addAll(validationQueryParam.getNegativeCase().getTestOps());

                    ExpectResponse tempResponse = validationQueryParam.getNegativeCase().getInvalidCase().get(index).getHandleResponse();
                    if(tempResponse.getStatusCode()==200) {
                        if(exeHandleResponse==null || exeHandleResponse.getStatusCode()==0) exeHandleResponse=tempResponse;
                        else{
                            exeHandleResponse.setFailLevel(tempResponse.getFailLevel());
                            exeHandleResponse.setFailureMessage(tempResponse.getFailureMessage());
                            exeHandleResponse.setStatusCode(tempResponse.getStatusCode());
                            if(exeHandleResponse.getResponseChecks()!=null)exeHandleResponse.getResponseChecks().addAll(tempResponse.getResponseChecks());
                            else exeHandleResponse.setResponseChecks(tempResponse.getResponseChecks());
                        }
                    }
                    if(validationQueryParam.getNegativeCase().getInvalidCase().get(index).getTestOps()!=null)exeTestOps.addAll(validationQueryParam.getNegativeCase().getInvalidCase().get(index).getTestOps());
                    
                    if(index!=0) Log.log(Log.getIndent() + "(invalid data --" + key + ":" + validationQueryParam.getNegativeCase().getInvalidCase().get(index).getValue() + ")");

                    if(validationQueryParam.getKey().equals("filter")) cacheFilter=validationQueryParam.getNegativeCase().getInvalidCase().get(index).getValue();
                }
                else if (index != -1)
                {
                    params.put(validationQueryParam.getKey(), URLEncoder.encode(validationQueryParam.getValidCase().get(index), "UTF-8").replace("+", "%20"));
                    if(index!=0) Log.log(Log.getIndent() + "(valid data --" + key + ":" + validationQueryParam.getValidCase().get(index) + ")");
                    if(validationQueryParam.getKey().equals("filter")) cacheFilter=validationQueryParam.getValidCase().get(index);
                }
            }
        }

        if (!requestForm.isEmpty())
        {
            for (LocalValue formParam : requestForm)
            {
                wc.addParamToForm(formParam.getKey(), formParam.getRealValue(testData));
            }
        }

        if (!validationRequestForm.isEmpty())
        {
            for (ValidationLocalValue validationRequestFormData : validationQueryParams)
            {
                String key = validationRequestFormData.getKey();
                int index;
                int size = 0;
                int validSize = 0;
                int invalidSize = 0;
                if (validationRequestFormData.getValidCase() != null && validationRequestFormData.getValidCase().size() != 0)
                {
                    validSize = validationRequestFormData.getValidCase().size();
                }
                if (validationRequestFormData.getNegativeCase() != null && validationRequestFormData.getNegativeCase().getInvalidCase().size() != 0)
                {
                    invalidSize = validationRequestFormData.getNegativeCase().getInvalidCase().size();
                }
                size = validSize + invalidSize;
                if (!validationProgress.containsKey(key))
                {
                    if (size != 0)
                    {
                        if (validSize != 0)
                            temp = new Progress(0, size, true, temp, null);
                        else
                            temp = new Progress(-1, size, false, temp, null);
                        if (temp.getPre() != null) temp.getPre().setNext(temp);
                        validationProgress.put(key, temp);
                        if (cur == null) cur = temp;
                    }
                }
                index = validationProgress.get(key).getCur();
                if (index >= validSize)
                {
                    index = index - validSize;
                    wc.addParamToForm(validationRequestFormData.getKey(), validationRequestFormData.getNegativeCase().getInvalidCase().get(index).getValue());
                    
                    exeHandleResponse = new ExpectResponse(validationRequestFormData.getNegativeCase().getHandleResponse());
                    exeTestOps = new ArrayList<TestOp>();
                    if(validationRequestFormData.getNegativeCase().getTestOps()!=null) exeTestOps.addAll(validationRequestFormData.getNegativeCase().getTestOps());

                    ExpectResponse tempResponse = validationRequestFormData.getNegativeCase().getInvalidCase().get(index).getHandleResponse();
                    if(tempResponse.getStatusCode()==200) {
                        if(exeHandleResponse==null || exeHandleResponse.getStatusCode()==0) exeHandleResponse=tempResponse;
                        else{
                            exeHandleResponse.setFailLevel(tempResponse.getFailLevel());
                            exeHandleResponse.setFailureMessage(tempResponse.getFailureMessage());
                            exeHandleResponse.setStatusCode(tempResponse.getStatusCode());
                            if(exeHandleResponse.getResponseChecks()!=null)exeHandleResponse.getResponseChecks().addAll(tempResponse.getResponseChecks());
                            else exeHandleResponse.setResponseChecks(tempResponse.getResponseChecks());
                        }
                    }
                    if(validationRequestFormData.getNegativeCase().getInvalidCase().get(index).getTestOps()!=null)exeTestOps.addAll(validationRequestFormData.getNegativeCase().getInvalidCase().get(index).getTestOps());
                    
                    if(index!=0) Log.log(Log.getIndent() + "(invalid data --" + key + ":" + validationRequestFormData.getNegativeCase().getInvalidCase().get(index).getValue() + ")");
                }
                else if (index != -1)
                {
                    wc.addParamToForm(validationRequestFormData.getKey(), validationRequestFormData.getValidCase().get(index));
                    if(index!=0) Log.log(Log.getIndent() + "(valid data --" + key + ":" + validationRequestFormData.getValidCase().get(index) + ")");
                }
            }
        }

        if (jsonPayload != null && jsonPayload.getBaseNode() != null)
        {
            if (jsonPayload.getReplaceJsonField().isEmpty() && jsonPayload.getValidationReplaceJsonField().isEmpty())
            {
                wc.setJsonPayloadString(jsonPayload.getBaseNode());
            }
            else
            {
                JsonNode tempNode = null;
                ObjectMapper tempMapper = new ObjectMapper();
                try
                {
                    tempNode = tempMapper.readTree(jsonPayload.getBaseNode());
                }
                catch (JsonProcessingException e)
                {
                    Log.log(Log.getIndent() + "FAIL - " + e.getMessage());
                }
                catch (IOException e)
                {
                    Log.log(Log.getIndent() + "FAIL - " + e.getMessage());
                }

                if (!jsonPayload.getReplaceJsonField().isEmpty())
                {

                    for (LocalValue replaceParam : jsonPayload.getReplaceJsonField())
                    {
                        String value = replaceParam.getRealValue(testData);
                        try
                        {
                            tempNode = JsonUtil.setJsonValue(tempNode, replaceParam.getKey(), value);
                        }
                        catch (Exception e)
                        {
                            Log.log(Log.getIndent() + "FAIL - " + e.getMessage());
                        }
                    }
                }
                if (!jsonPayload.getValidationReplaceJsonField().isEmpty())
                {
                    for (ValidationLocalValue validationJsonReplace : jsonPayload.getValidationReplaceJsonField())
                    {
                        String key = validationJsonReplace.getKey();
                        int index;
                        int size = 0;
                        int validSize = 0;
                        int invalidSize = 0;
                        if (validationJsonReplace.getValidCase() != null && validationJsonReplace.getValidCase().size() != 0)
                        {
                            validSize = validationJsonReplace.getValidCase().size();
                        }
                        if (validationJsonReplace.getNegativeCase() != null && validationJsonReplace.getNegativeCase().getInvalidCase().size() != 0)
                        {
                            invalidSize = validationJsonReplace.getNegativeCase().getInvalidCase().size();
                        }
                        size = validSize + invalidSize;
                        if (!validationProgress.containsKey(key))
                        {
                            if (size != 0)
                            {
                                if (validSize != 0)
                                    temp = new Progress(0, size, true, temp, null);
                                else
                                    temp = new Progress(-1, size, false, temp, null);
                                if (temp.getPre() != null) temp.getPre().setNext(temp);
                                validationProgress.put(key, temp);
                                if (cur == null) cur = temp;
                            }
                        }
                        index = validationProgress.get(key).getCur();
                        if (index >= validSize)
                        {
                            index = index - validSize;
                            try
                            {
                                tempNode = JsonUtil.setJsonValue(tempNode, key, validationJsonReplace.getNegativeCase().getInvalidCase().get(index).getValue());
                                if(index!=0) Log.log(Log.getIndent() + "(invalid data --" + key + ":" + validationJsonReplace.getNegativeCase().getInvalidCase().get(index).getValue() + ")");
                            }
                            catch (Exception e)
                            {
                                Log.log(Log.getIndent() + "FAIL - " + e.getMessage());
                            }
                            
                            if(exeHandleResponse==null || exeHandleResponse.getStatusCode()==0) exeHandleResponse = new ExpectResponse(validationJsonReplace.getNegativeCase().getHandleResponse());
                            exeTestOps = new ArrayList<TestOp>();
                            if(validationJsonReplace.getNegativeCase().getTestOps()!=null) exeTestOps.addAll(validationJsonReplace.getNegativeCase().getTestOps());

                            ExpectResponse tempResponse = validationJsonReplace.getNegativeCase().getInvalidCase().get(index).getHandleResponse();
                            if(tempResponse!=null && tempResponse.getStatusCode()==200) {
                                if(exeHandleResponse==null || exeHandleResponse.getStatusCode()==0) exeHandleResponse=tempResponse;
                                else{
                                    exeHandleResponse.setFailLevel(tempResponse.getFailLevel());
                                    exeHandleResponse.setFailureMessage(tempResponse.getFailureMessage());
                                    exeHandleResponse.setStatusCode(tempResponse.getStatusCode());
                                    if(exeHandleResponse.getResponseChecks()!=null)exeHandleResponse.getResponseChecks().addAll(tempResponse.getResponseChecks());
                                    else exeHandleResponse.setResponseChecks(tempResponse.getResponseChecks());
                                }
                                
                            }
                            if(validationJsonReplace.getNegativeCase().getInvalidCase().get(index).getTestOps()!=null)exeTestOps.addAll(validationJsonReplace.getNegativeCase().getInvalidCase().get(index).getTestOps());
                            
                        }
                        else if (index != -1)
                        {
                            try
                            {
                                tempNode = JsonUtil.setJsonValue(tempNode, key, validationJsonReplace.getValidCase().get(index));
                                if(index!=0) Log.log(Log.getIndent() + "(valid data --" + key + ":" + validationJsonReplace.getValidCase().get(index) + ")");
                            }
                            catch (Exception e)
                            {
                                Log.log(Log.getIndent() + "FAIL - " + e.getMessage());
                            }
                        }
                    }
                }
                wc.setJsonPayloadString(tempNode.toString());
            }
        }
        
        if(exeHandleResponse==null || exeHandleResponse.getStatusCode()==0) exeHandleResponse=handleResponse;
        if (exeHandleResponse != null && exeHandleResponse.getStatusCode()!=0)
        {

            try
            {

                   wc.httpMethod(method, (new StringBuilder(baseURL).append(path)).toString(), params);
            }
            catch (Exception e1)
            {
                Log.log(Log.getIndent() + "FAIL - " + e1.getMessage());
            }
            int statusCode = exeHandleResponse.getStatusCode();
            if (statusCode != wc.getResponseStatusCode())
            {
                Log.log(Log.getIndent() + "FAIL - it returned status code " + wc.getResponseStatusCode() + ", instead of right status code " + statusCode);
            }
            else
            {
                response = wc.getResponseContentString();

                try
                {
                    jsonObj = mapper.readTree(response);
                }
                catch (Exception e)
                {
                    Log.log(Log.getIndent() + "FAIL - " + e.getMessage());
                    return true;
                }
                /*
                 * Check response
                 */
                for (ConditionCheck responseCheck : exeHandleResponse.getResponseChecks())
                {
                    if (!responseCheck.execute(jsonObj, testData))
                    {
                        switch (exeHandleResponse.getFailLevel())
                        {
                            case ERROR:
                                Log.log(Log.getIndent() + "FAIL - Not get expected response " + responseCheck.toString());
                                break;
                            case WARNING:
                                Log.log(Log.getIndent() + "WARNING - Not get expected response " + responseCheck.toString());
                                break;
                            default:
                                break;
                        }
                        Log.log("  Response from request:" + response);
                        if (exeHandleResponse.getFailureMessage() != null && !exeHandleResponse.getFailureMessage().isEmpty())
                        {
                            Log.log(Log.getIndent() + exeHandleResponse.getFailureMessage());
                        }
                    }
                }
            }
        }

        if(exeTestOps==null || exeTestOps.size()==0) exeTestOps=testOps;
        if (exeTestOps!=null || exeTestOps.size()!=0)
        {
            for (TestOp testOp : exeTestOps)
            {
                testOp.execute(jsonObj, testData);
            }
        }


        if (!endConditons.isEmpty())
        {
            end = true;
            for (EndConditon endConditon : endConditons)
            {
                for (ConditionCheck endCheck : endConditon.getEndCheck())
                {
                    end = end && endCheck.execute(jsonObj, testData);
                }
                if (end == true)
                {
                    for (ConditionCheck finalCheck : endConditon.getFinalCheck())
                    {
                        if (!finalCheck.execute(jsonObj, testData))
                        {
                            switch (exeHandleResponse.getFailLevel())
                            {
                                case ERROR:
                                    Log.log(Log.getIndent() + "FAIL - Not get expected response " + finalCheck.toString());
                                    break;
                                case WARNING:
                                    Log.log(Log.getIndent() + "WARNING - Not get expected response " + finalCheck.toString());
                                    break;
                                default:
                                    break;
                            }
                            Log.log("  Response from request:" + response);
                            if (exeHandleResponse.getFailureMessage() != null && !exeHandleResponse.getFailureMessage().isEmpty())
                            {
                                Log.log(Log.getIndent() + exeHandleResponse.getFailureMessage());
                            }
                        }

                    }
                    break;
                }
            }
            if (end == false) this.execute(testNumber, baseURL, wc, testData);
        }

        if (cur != null)
        {
            if (cur.getCur() >= cur.getLimit() - 1)
            {
                do
                {
                    if (cur.isFirstElementAsDefault())
                        cur.setCur(0);
                    else
                        cur.setCur(-1);

                    cur = cur.getNext();

                    if (cur == null) return cur == null;
                    cur.setCur(cur.getCur() + 1);
                } while (cur.getCur() >= cur.getLimit());
            }

            else
                cur.setCur(cur.getCur() + 1);

            return cur == null;
        }
        return cur == null;
    }
}
