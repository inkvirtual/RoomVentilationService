package com.homeAutomation.resources;

import com.homeAutomation.service.Main;

public class ResourcesHelper {
    private String resourcesPath;

    public ResourcesHelper(String resourcesPath) {
        if (resourcesPath == null || resourcesPath.length() == 0)
            throw new IllegalArgumentException(
                    "Null or empty \"resourcesPath\" argument");

        init(resourcesPath);
    }

    private void init(String resourcesPath) {
        this.resourcesPath = normalizePath(resourcesPath);
    }

    public String normalizePath(String path) {
        if (path.charAt(path.length() - 1) != '/')
            path += '/';
        return path;
    }

    public String getResourcesPath() {
        return resourcesPath;
    }

    public String getFullPath(String fileName) {
        if (null == fileName || fileName.length() == 0) {
            Main.LOGGER.error("Null or empty <filename> argument");
            return null;
        }

        return resourcesPath + fileName;
    }

    public boolean checkPropsFileNameValid(String propsFileName) {
        if (propsFileName == null || propsFileName.length() == 0)
            throw new IllegalArgumentException("Null or empty properties file name");

        try {
            String[] tokens = propsFileName.split(".");

            if (tokens.length == 1)
                throw new IllegalArgumentException("Invalid properties file name:" + propsFileName);

            if (!propsFileName.endsWith(".properties"))
                throw new IllegalArgumentException("Invalid properties file name extension");

        } catch (Exception ex) {
            throw ex;
        }

        return true;
    }

    public String getSubstring(String content, String leftParam, String rightParam) {
        if (content == null || content.length() == 0)
            throw new IllegalArgumentException("Null or empty <content>");

//        if (leftParam == null || leftParam.length() == 0)
//            throw new IllegalArgumentException("Null or empty leftParam");
//
//        if (rightParam == null || rightParam.length() == 0)
//            throw new IllegalArgumentException("Null or empty rightParam");

        int beginIndex = 0;
        String updatedContent = content;

        if (leftParam != null && leftParam.length() > 0) {
            beginIndex = content.indexOf(leftParam);
            updatedContent = content.substring(beginIndex + leftParam.length());
        }

        if (beginIndex == -1)
            throw new IllegalArgumentException("Incorrect <leftParam>");

        if (updatedContent == null)
            return null;

        int endIndex = updatedContent.length();

        if (rightParam != null && rightParam.length() > 0)
            endIndex = updatedContent.indexOf(rightParam);

        if (endIndex == -1)
            throw new IllegalArgumentException("Incorrect <rightParam>");

        return updatedContent.substring(0, endIndex);
    }
}
