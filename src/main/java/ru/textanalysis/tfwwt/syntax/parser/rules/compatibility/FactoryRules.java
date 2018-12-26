package ru.textanalysis.tfwwt.syntax.parser.rules.compatibility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.textanalysis.tfwwt.rules.compatibility.IRules;
import ru.textanalysis.tfwwt.rules.compatibility.RFC;

public class FactoryRules {
    private static Logger log = LoggerFactory.getLogger(FactoryRules.class);
    private static IRules instance = null;

    private FactoryRules() {}

    /**
     * Заменяет IRules на стороннюю реализацию, если instance не было инициализирован
     * @param instance
     * @return
     */
    public synchronized static boolean setRulesImpl(IRules instance) {
        if (FactoryRules.instance == null) {
            FactoryRules.instance = instance;
            log.debug("Init IRules implement " + instance.getClass().getName());
            return true;
        }
        return false;
    }

    public synchronized static IRules getInstance() {
        if (instance == null) {
            if (!setRulesImpl(new RFC())) {
                log.warn("Not init IRules implement " + RFC.class.getName());
            }
        }

        return instance;
    }
}
