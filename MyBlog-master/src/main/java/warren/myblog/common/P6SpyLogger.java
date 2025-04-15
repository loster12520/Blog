package warren.myblog.common;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 自定义 P6Spy SQL 日志格式（带颜色）
 * author: Warren
 */
public class P6SpyLogger implements MessageFormattingStrategy {

    // ANSI 颜色代码
    private static final String RESET = "\u001B[0m";  // 重置颜色
    private static final String RED = "\u001B[31m";   // 红色（高亮错误）
    private static final String GREEN = "\u001B[32m"; // 绿色（SQL 语句）
    private static final String YELLOW = "\u001B[33m";// 黄色（执行时间）
    private static final String BLUE = "\u001B[34m";  // 蓝色（分类）
    private static final String CYAN = "\u001B[36m";  // 青色（连接 ID）

    /**
     * 自定义 SQL 日志格式（紧凑且带颜色）
     *
     * @param connectionId 连接 ID
     * @param now 当前时间（P6Spy 传递的）
     * @param elapsed SQL 执行时间（ms）
     * @param category SQL 类型（如 statement、commit、rollback）
     * @param prepared 预编译 SQL（带 ? 占位符）
     * @param sql 真实 SQL 语句（占位符替换后的）
     * @param url 数据库连接 URL
     * @return 格式化后的日志字符串
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        // 过滤空 SQL
        if (sql == null || sql.trim().isEmpty()) {
            return "";
        }

        // 格式化当前时间
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // 去除 SQL 语句中的多余换行
        String cleanedSql = sql.replaceAll("\\s+", " ").trim();

        // 构造紧凑型带颜色的日志输出
        return String.format(
                "%s[%s]%s | %s耗时: %d ms%s | %s连接: %d%s | %s分类: %s%s | %sSQL: %s%s;",
                CYAN, currentTime, RESET,           // 时间（青色）
                YELLOW, elapsed, RESET,            // 执行时间（黄色）
                BLUE, connectionId, RESET,         // 连接 ID（蓝色）
                RED, category, RESET,              // 分类（红色）
                GREEN, cleanedSql, RESET           // SQL 语句（绿色）
        );
    }
}
