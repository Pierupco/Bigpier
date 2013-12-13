package com.pier.support.util;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


public class WordUtil {
    private static final int MAX_WORD_LENGTH = 70;
    private static final String WITHIN_OP = "WITHIN-";
    private static final String NOT_OP = "NOT";
    private static final String DQUOTE = "\"";
    private static final String DELIMITERS = " \"";
    protected static Logger eblogger = Logger.getLogger(WordUtil.class.getName());
    // Two different ways to initialize collection here.
    private static Set<String> BOOLEANS_OPS = new HashSet<String>(Arrays.asList("AND", "OR", "NOT"));
    private static Set<UnicodeBlock> CJKUnicodeBlocks = new HashSet<UnicodeBlock>() {{
        add(UnicodeBlock.CJK_COMPATIBILITY);
        add(UnicodeBlock.CJK_COMPATIBILITY_FORMS);
        add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
        add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT);
        add(UnicodeBlock.CJK_RADICALS_SUPPLEMENT);
        add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
        add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
        add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
        add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
        add(UnicodeBlock.KANGXI_RADICALS);
        add(UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS);
        add(UnicodeBlock.KATAKANA);
        add(UnicodeBlock.HIRAGANA);
        add(UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS);
    }};

    private static boolean isValidPhrase(String phrase) {
        if (DQUOTE.equals(phrase) || phrase.length() == 0 || (phrase.length() == 1 && !Character.isLetterOrDigit(phrase.charAt(0)))) {
            return (false);
        }
        return (true);
    }

    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * Extract a set of terms from the string.  A term can be delimited by spaces
     * or between double quotes
     *
     * @param input input string to be parsed
     * @return set of terms extracted
     */
    public static List<String> extract(String input) {
        List<String> terms = new ArrayList<String>();
        if (input == null || input.length() == 0) return (terms);

        try {
            input = input.replaceAll("\\s+", " ");
            StringTokenizer st = new StringTokenizer(input, DELIMITERS, true);

            while (st.hasMoreTokens()) {
                String phrase = st.nextToken();
                if (phrase.equals(" ")) continue;

                // if the token is a double quote, extract the content between
                // quotes or till the end of the string
                if (phrase.equals(DQUOTE)) {
                    if (!st.hasMoreTokens()) break;
                    phrase = st.nextToken(DQUOTE).trim();
                    if (isValidPhrase(phrase)) {
                        terms.add(phrase);
                    }
                    if (!st.hasMoreTokens()) continue;
                    phrase = st.nextToken(DELIMITERS);
                }
                // make sure the phrase is not empty before we save it
                phrase = phrase.trim();
                if (isValidPhrase(phrase)) {
                    terms.add(phrase);
                }
            }
        } catch (Exception e) {
            eblogger.log(Level.WARNING, "Cannot extract input=" + input, e);
        }
        return (terms);
    }

    public static List<String> extractSearchTerms(String input) {
        List<String> searchTerms = new ArrayList<String>();
        List<String> terms = extract(input);

        if (terms == null || terms.size() == 0) return (searchTerms);

        Iterator<String> iter = terms.iterator();
        while (iter.hasNext()) {
            String token = iter.next();
            if (token.startsWith(WITHIN_OP)) continue;
            if (NOT_OP.equals(token)) {
                if (iter.hasNext()) iter.next();
                continue;
            }
            if (!BOOLEANS_OPS.contains(token)) {
                searchTerms.add(token.toLowerCase());
            }
        }
        return (searchTerms);
    }

    public static List<String> extractSearchTerms(List<String> input) {
        List<String> searchTerms = new ArrayList<String>();
        if (input == null || input.size() == 0) return (searchTerms);

        for (String str : input) {
            searchTerms.addAll(WordUtil.extractSearchTerms(str));
        }
        return (searchTerms);
    }

    public static String isSupportedQuery(String query) {
        if (query == null) return (null);

        query = query.trim();
        if (query.indexOf(WITHIN_OP) > 0 && query.indexOf('"') == -1) {
            String[] terms = query.split("\\s+");
            if (terms.length == 3 && terms[1].startsWith(WITHIN_OP)) {
                String near = terms[1].substring(WITHIN_OP.length());
                try {
                    int n = Integer.parseInt(near);
                    query = "\"" + terms[0] + " " + terms[2] + "\"~" + n;
                    return (query);
                } catch (Exception e) {
                    eblogger.fine("Invalid WITHIN doc parameters =" + near);
                }
            }
        }
        return (null);
    }

    public static String isSupportedQuery(List<String> terms) {
        if (terms != null && terms.size() == 1) {
            return (decorateCJKSearchTerm(isSupportedQuery(terms.get(0))));
        }
        return (null);
    }

    /**
     * For all the words in the text, break up the words that are longer than "limit" characters
     */
    public static String splitLongWords(String text, int limit) {
        if (text == null || text.length() <= limit) return (text);
        StringBuilder sb = new StringBuilder();
        String[] words = text.split(" ");
        for (String word : words) {
            if (word.length() <= limit) {
                sb.append(word);
                sb.append(" ");
            } else {
                int count = word.length() / limit;
                for (int i = 0; i < count; i++) {
                    sb.append(word.substring(i * limit, (i + 1) * limit));
                    sb.append(" ");
                }
                if (word.length() % limit > 0) {
                    sb.append(word.substring(count * limit));
                    sb.append(" ");
                }
            }
        }
        return (sb.toString());
    }

    public static String revertSupportedQuery(String query) {
        if (query == null) return ("");

        query = query.trim();
        if (query.charAt(0) == '"' && query.indexOf("\"~") > 0) {
            String[] terms = query.split("\\s+");
            if (terms.length == 2) {
                int idx = terms[1].indexOf("\"~");
                if (idx > 0) {
                    try {
                        int n = Integer.parseInt(terms[1].substring(idx + 2));
                        String term2 = terms[1].substring(0, idx);
                        query = terms[0].replaceAll("\"", "") + " " + WITHIN_OP + n + " " + term2;
                    } catch (Exception e) {
                        eblogger.fine("Cannot convert WITHIN query =" + query);
                    }
                }
            }
        }
        return (query);
    }

    public static String splitLongWords(String text) {
        return (splitLongWords(text, MAX_WORD_LENGTH));
    }

    /**
     * Code copied from JSONObject.java available at http://www.json.org/java/
     * Produce a string in double quotes with backslash sequences in all the
     * right places. A backslash will be inserted within </, allowing JSON
     * text to be delivered in HTML. In JSON text, a string cannot contain a
     * control character or an unescaped quote or backslash.
     *
     * @param string A String
     * @return A String correctly formatted for insertion in a JSON text.
     */
    public static String toJsonString(String string) {
        if (string == null || string.length() == 0) {
            return "\"\"";
        }

        char b;
        char c = 0;
        int i;
        int len = string.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            b = c;
            c = string.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    if (b == '<') {
                        sb.append('\\');
                    }
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ' || (c >= '\u0080' && c < '\u00a0') ||
                            (c >= '\u2000' && c < '\u2100')) {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }

    public static String commaSepString(Iterable things) {
        if (null == things) {
            return "";
        }
        String res = "";
        for (Object thing : things) {
            res += ((thing == null) ? "" : thing.toString()) + ",";
        }
        if (res.length() > 0) {
            res = res.substring(0, res.length() - 1); // removing the extra "," at the end
        }
        return res;
    }

    public static String commaSepString(Object[] things) {
        return (null == things) ? "" : commaSepString(Arrays.asList(things));
    }

    public static String decorateCJKSearchTerm(String s) {
        if (s == null || s.contains("\"")) {
            return s;
        }
        StringBuilder sb = new StringBuilder();
        boolean hasChinese = false;
        for (char c : s.toCharArray()) {
            if (CJKUnicodeBlocks.contains(UnicodeBlock.of(c))) {
                if (sb.length() > 0) sb.append(" ");
                hasChinese = true;
            }
            sb.append(c);
        }
        return hasChinese ? String.format("\"%s\"", sb.toString()) : sb.toString();
    }

    public static Integer safeParseInt(String str, Integer defaultValue) {
        try {
            int res = Integer.parseInt(str);
            return res;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Long safeParseLong(String str, Long defaultValue) {
        try {
            long res = Long.parseLong(str);
            return res;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static void main(String[] args) {
        String[] test = new String[]{
                "first second third",
                "first\"second third\"",
                "first\"second third\"forth",
                "test one\"two three\" \"four five",
                "test \"\"mary john\" two one\"",
                "test \"cats dogs\"\"mary john\" one\""
        };

        for (String s : test) {
            System.out.println("=====> extract: " + s);
            for (String t : extract(s)) {
                System.out.println(t);
            }
        }

        test = new String[]{
                "first second third",
                "cats AND dogs",
                "mouse NOT dogs",
                "cat mouse NOT dogs birds NOT",
                "cats WITHIN-15 dogs"
        };
        for (String s : test) {
            System.out.println(s + " === extractSearchTerms to ===> " + extractSearchTerms(s));
        }

        test = new String[]{
                "first second third",
                "cats AND dogs",
                "mouse cats WITHIN-15 dogs",
                "cats WITHIN-15 dogs"
        };
        for (String s : test) {
            System.out.println(s + " === convert to ===> " + isSupportedQuery(s));
        }

        String longString = "abcdefghijklmnopqrstuvwxyz 123 12312312312313212 abc123 joy to the world";

        System.out.println("long string =" + splitLongWords(longString, 4));
    }

    public static float safeParseFloat(String str, Float defaultValue) {
        try {
            float res = Float.parseFloat(str);
            return res;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String getNormalizedEmails(String pEmails) {
        if (pEmails == null)
            return null;
        String[] emails = pEmails.split(",");
        return removeEmptyStrings(emails);

    }

    public static String removeEmptyStrings(String[] pStrings) {
        if (pStrings == null) {
            return null;
        }
        String strings = "";
        for (String str : pStrings) {
            if (str != null && !(str.trim()).isEmpty()) {
                strings = strings + str.trim() + ",";
            }
        }
        if (strings.length() > 0) {
            strings = strings.substring(0, strings.length() - 1);
        }
        return strings;
    }

    // moved from searchQueryForm.java so that it can be used for both help and search : PRGDCP-3763
    public static String sanitizeSearchValue(String s) {
        // Fix for PRGDCP-1976
        if (s != null && !s.isEmpty()) {
            try {// if the string is encoded, decode it first and see if there is any script tag in it

                Pattern p = Pattern.compile("(?).*<\\s*/?\\s*script\\s*>.*", Pattern.CASE_INSENSITIVE);
                String decodeString = URLDecoder.decode(s, "UTF-8");
                if (p.matcher(decodeString).matches()) { // if script tag present, replace the original with the decoded string
                    s = decodeString;
                }
            } catch (UnsupportedEncodingException e) {
                eblogger.log(Level.INFO, "String could not be decoded: UnsupportedEncodingException XssFilter.sanitizeSearchValue(): " + s);
            } catch (Exception e) {
                eblogger.log(Level.INFO, "String could not be decoded  XssFilter.sanitizeSearchValue(): " + s);
            }

            s = s.replace("\\", "");
            s = s.replaceAll("(?i)<\\s*/?\\s*script\\s*>", "");
            // replace '> "> ' > with blanks
            s = s.replaceAll("[\"']\\s*>", "");

        }
        return (s);
    }


}

