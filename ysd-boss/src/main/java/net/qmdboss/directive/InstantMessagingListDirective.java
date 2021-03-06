package net.qmdboss.directive;

import freemarker.core.Environment;
import freemarker.template.*;
import net.qmdboss.entity.InstantMessaging;
import net.qmdboss.entity.InstantMessaging.InstantMessagingType;
import net.qmdboss.service.InstantMessagingService;
import net.qmdboss.util.DirectiveUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component("instantMessagingListDirective")
public class InstantMessagingListDirective implements TemplateDirectiveModel {
	
	public static final String TAG_NAME = "instant_messaging_list";
	private static final String INSTANT_MESSAGING_TYPE_PARAMETER_NAME = "type";
	private static final String COUNT_PARAMETER_NAME = "count";
	
	@Resource(name = "instantMessagingServiceImpl")
	private InstantMessagingService instantMessagingService;

	@SuppressWarnings("unchecked")
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		String instantMessagingTypeString = DirectiveUtil.getStringParameter(INSTANT_MESSAGING_TYPE_PARAMETER_NAME, params);
		Integer count = DirectiveUtil.getIntegerParameter(COUNT_PARAMETER_NAME, params);
		
		InstantMessagingType instantMessagingType = null;
		if (StringUtils.isNotEmpty(instantMessagingTypeString)) {
			instantMessagingType = InstantMessagingType.valueOf(instantMessagingTypeString);
		}
		
		List<InstantMessaging> instantMessagingList = instantMessagingService.getInstantMessagingList(instantMessagingType, count);
		
		if (body != null && instantMessagingList != null) {
			if (loopVars.length > 0) {
				loopVars[0] = ObjectWrapper.BEANS_WRAPPER.wrap(instantMessagingList);
			}
			body.render(env.getOut());
		}
	}

}