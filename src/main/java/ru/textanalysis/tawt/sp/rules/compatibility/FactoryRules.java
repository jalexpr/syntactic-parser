package ru.textanalysis.tawt.sp.rules.compatibility;

import lombok.extern.slf4j.Slf4j;
import ru.textanalysis.tawt.rfc.model.Rule;

@Slf4j
public class FactoryRules {

	private static Rule instance = null;

	private FactoryRules() {
	}

	/**
	 * Заменяет Rule на стороннюю реализацию, если instance не было инициализирован
	 *
	 * @param instance
	 *
	 * @return
	 */
	public synchronized static boolean setRulesImpl(Rule instance) {
		if (FactoryRules.instance == null) {
			FactoryRules.instance = instance;
			log.debug("Init Rule implement " + instance.getClass().getName());
			return true;
		}
		return false;
	}

	public synchronized static Rule getInstance() {
		if (instance == null) {
//            if (!setRulesImpl(new RFC())) {
//                log.warn("Not init Rule implement " + RFC.class.getName());
//            }
		}

		return instance;
	}
}
