package fr.lyon1.avote.logic.service;

import fr.lyon1.avote.logic.enums.HtmlTemplateEnum;
import fr.lyon1.avote.logic.model.entity.Poll;
import fr.lyon1.avote.logic.model.entity.User;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class VelocityService {

    private VelocityService() {
    }

    public static String createPollListHtml(List<Poll> pollList, HtmlTemplateEnum templateEnum) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        ve.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        ve.init();
        Template t = ve.getTemplate(templateEnum.getFileName(), StandardCharsets.UTF_8.name());
        VelocityContext context = new VelocityContext();
        context.put("pollList", pollList);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString();
    }

    public static String createUserHtml(User user, HtmlTemplateEnum templateEnum) {
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        ve.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, "org.apache.velocity.runtime.log.Log4JLogChute");
        ve.setProperty("runtime.log.logsystem.log4j.logger", "velocity");
        ve.init();
        Template t = ve.getTemplate(templateEnum.getFileName(), StandardCharsets.UTF_8.name());
        VelocityContext context = new VelocityContext();
        context.put("user", user);
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        return writer.toString();
    }
}
