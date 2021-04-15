package gov.nist.hit.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class HITStatsLogger {

    private static final Logger logger = LoggerFactory.getLogger("HIT_STATS_LOGGER");
    private static final String USERNAME_DEFAULT = "";
    private static final String ORG_DEFAULT = "";
    private static final String OP_DEFAULT = "";
    private static final String PARAM_DEFAULT = "";

    /***
     * Create a log entry using username, organization, operation code, list of parameters
     * @param username
     * @param organization
     * @param operation
     * @param parameters
     */
    public static void log(String username, String organization, String operation, List<String> parameters) {
        logger.info(
            stringify(
                Arrays.asList(
                    stringify(
                        Arrays.asList(
                                getValue(username, USERNAME_DEFAULT),
                                getValue(organization, ORG_DEFAULT),
                                getValue(operation, OP_DEFAULT)
                        )
                    ),
                    getValue(parameters, PARAM_DEFAULT)
                )
            )
        );
    }

    /***
     * Create a log entry using username, organization, operation code, single parameter
     * @param username
     * @param organization
     * @param operation
     * @param parameter
     */
    public static void log(String username, String organization, String operation, String parameter) {
        log(username, organization, operation, Collections.singletonList(parameter));
    }

    /***
     * Create a log entry using username, organization, operation code
     * @param username
     * @param organization
     * @param operation
     */
    public static void log(String username, String organization, String operation) {
        log(username, organization, operation, Collections.emptyList());
    }

    /***
     * Create a log entry using username, organization, operation code, parameters (spread)
     * @param username
     * @param organization
     * @param operation
     */
    public static void log(String username, String organization, String operation, String ...values) {
        log(username, organization, operation, Arrays.asList(values));
    }

    /***
     * Create a log entry using operation code, parameters (spread)
     * @param operation
     * @param values
     */
    public static void logOp(String operation, String ...values) {
        log(null, null, operation, Arrays.asList(values));
    }

    /***
     * Create a log entry using operation code
     * @param operation
     */
    public static void logOp(String operation) {
        log(null, null, operation, Collections.emptyList());
    }

    private static String getValue(String str, String _default) {
        return escape(str == null || str.isEmpty() ? _default : str);
    }

    private static String getValue(List<String> params, String _default) {
        return params == null || params.isEmpty() ?
                escape(_default) :
                stringify(params
                        .stream()
                        .map(HITStatsLogger::escape)
                        .collect(Collectors.toList())
                );
    }

    private static String stringify(List<String> values) {
        return String.join("\t", values);
    }

    private static String escape(String str) {
        return str
                .replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\t", "\\t");
    }

}
