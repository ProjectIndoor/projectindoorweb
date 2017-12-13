package de.hftstuttgart.projectindoorweb.positionCalculator.internal.utility;

import de.hftstuttgart.projectindoorweb.persistence.entities.Parameter;
import de.hftstuttgart.projectindoorweb.persistence.entities.Project;
import de.hftstuttgart.projectindoorweb.positionCalculator.internal.CorrelationMode;

import java.util.List;

public class ProjectParameterResolver {

    public static Object retrieveParameterValue(Project project, String parameterName, Class<? extends Object> parameterValueClass){

        List<Parameter> projectParameters = project.getProjectParameters();

        Object result = null;
        for (Parameter parameter:
             projectParameters) {
            if(parameter.getParameterName().equalsIgnoreCase(parameterName)){
                if(parameterValueClass.isAssignableFrom(Double.class)){
                    return getDoubleValue(parameter.getParameterValue());
                }else if(parameterValueClass.isAssignableFrom(Integer.class)){
                    return getIntValue(parameter.getParameterValue());
                }else if(parameterValueClass.isAssignableFrom(Boolean.class)){
                    return getBooleanValue(parameter.getParameterValue());
                }else if(parameterValueClass.isAssignableFrom(CorrelationMode.class)){
                    return getCorrelationMode(parameter.getParameterValue());
                }
            }
        }

        return result;

    }

    private static double getDoubleValue(String parameterValue){

        try{
            return Double.valueOf(parameterValue);
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            return -1.0;
        }

    }

    private static int getIntValue(String parameterValue){


        try{
            return Integer.valueOf(parameterValue);
        }catch(NumberFormatException ex){
            ex.printStackTrace();
            return -1;
        }
    }

    private static Boolean getBooleanValue(String parameterValue){

        if(parameterValue.equalsIgnoreCase("true")){
            return true;
        }
        if(parameterValue.equalsIgnoreCase("false")){
            return false;
        }

        return null;

    }

    private static CorrelationMode getCorrelationMode(String parameterValue){

        switch (parameterValue.toLowerCase()){
            /*
            * Currently (as of December 8th, 2017), our backend knows only the Euclidian correlation mode as
            * this was the only correlation mode implemented in the prototype.
            *
            * */
            case "euclidian": return CorrelationMode.EUCLIDIAN;
            default: return null;
        }

    }




}
