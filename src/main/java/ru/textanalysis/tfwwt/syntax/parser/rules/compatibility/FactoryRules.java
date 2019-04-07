package ru.textanalysis.tfwwt.syntax.parser.rules.compatibility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.textanalysis.tfwwt.rules.compatibility.Rule;
//import ru.textanalysis.tfwwt.rules.compatibility.RFC;

public class FactoryRules {
    private static Logger log = LoggerFactory.getLogger(FactoryRules.class);
    private static Rule instance = null;

    private FactoryRules() {}

    /**
     * Заменяет Rule на стороннюю реализацию, если instance не было инициализирован
     * @param instance
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
