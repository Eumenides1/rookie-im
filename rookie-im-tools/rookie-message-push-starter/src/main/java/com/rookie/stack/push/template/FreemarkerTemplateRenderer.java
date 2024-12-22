package com.rookie.stack.push.template;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

import java.io.StringWriter;
import java.util.Locale;

/**
 * Name：FreemarkerTemplateRenderer
 * Author：eumenides
 * Created on: 2024/12/22
 * Description:
 */
public class FreemarkerTemplateRenderer implements TemplateRenderer {

    private final Configuration freemarkerConfig;

    /**
     * 构造函数：支持自定义模板目录
     *
     * @param customTemplatePath 调用方自定义的模板路径，如果为空，则使用默认路径
     */
    public FreemarkerTemplateRenderer(String customTemplatePath) {
        this.freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);

        if (customTemplatePath != null && !customTemplatePath.isBlank()) {
            // 使用调用方自定义模板路径
            freemarkerConfig.setClassForTemplateLoading(getClass(), customTemplatePath);
        } else {
            // 使用 Starter 默认模板路径
            freemarkerConfig.setClassForTemplateLoading(getClass(), "/templates");
        }

        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setLocale(Locale.US);
        freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    @Override
    public String render(String templateName, Object model) {
        try {
            Template template = freemarkerConfig.getTemplate(templateName);
            StringWriter writer = new StringWriter();
            template.process(model, writer);
            return writer.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to render template", e);
        }
    }
}
