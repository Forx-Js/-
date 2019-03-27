package ht.service.auth.po;

/**
 * 功能点
 * Created by zeng.ling on 2017/11/18.
 */
public class FunctionInfo {

    private String functionId;
    private  String functionName;
    private String moduleId;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId;
    }
}
