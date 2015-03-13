package net.alureon.ircbutt.handler;

public class ButtFormatHandler {

    public String buttformat(String message) {
        String[] split = message.split(" ");
        replaceButt(split);
        if (split.length > 6) {
            boolean success = replaceButt(split);
            if (!success) {
                buttRetry(split);
            }
        }
        if (split.length >= 14) {
            boolean success = replaceButt(split);
            if (!success) {
                buttRetry(split);
            }
        }
        if (split.length >= 20) {
            boolean success = replaceButt(split);
            if (!success) {
                buttRetry(split);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String x : split) {
            if (!x.equalsIgnoreCase("buttbutt") && !x.equalsIgnoreCase("buttbutt!")
                    && !x.equalsIgnoreCase("buttbutt?") && !x.equalsIgnoreCase("buttbutt,")
                    && !x.equalsIgnoreCase("buttbutt.")) {
                sb.append(x).append(" ");
            }
        }
        return sb.toString();
    }

    private boolean replaceButt(String[] split) {
        int replace = (int) (Math.random()*split.length);
        if(!isAWordWeDontReplace(split[replace])) {
            if (split[replace].equalsIgnoreCase("buttbutt")) {
                split[replace] = "";
                return true;
            }

            StringBuilder sb = new StringBuilder("butt");
            String suffix = "";
            if (split[replace].endsWith("?")) {
                suffix = "?";
            } else if (split[replace].endsWith("ed")) {
                suffix = "ed";
            } else if (split[replace].endsWith("ing")) {
                suffix = "ing";
            } else if (split[replace].endsWith("ify")) {
                suffix = "ify";
            } else if (split[replace].endsWith("ly")) {
                suffix = "ly";
            } else if (split[replace].endsWith("er")) {
                suffix = "er";
            } else if (split[replace].endsWith("'s")) {
                suffix = "'s";
            } else if (split[replace].endsWith("s")) {
                suffix = "s";
            } else if (split[replace].endsWith("!")) {
                suffix = "!";
            } else if (split[replace].endsWith(".")) {
                suffix = ".";
            } else if (split[replace].endsWith("()")) {
                suffix = "()";
            } else if (split[replace].endsWith(")")) {
                suffix = ")";
            } else if (split[replace].endsWith(");")) {
                suffix = ");";
            } else if (split[replace].endsWith(":")) {
                suffix = ":";
            } else if (split[replace].endsWith(";")) {
                suffix = ";";
            } else if (split[replace].endsWith("\"")) {
                suffix = "\"";
            } else if (split[replace].endsWith("'")) {
                suffix = "'";
            } else if (split[replace].endsWith("*")) {
                suffix = "*";
            }
            sb.append(suffix);

            String prefix = "";
            if (split[replace].startsWith("#")) {
                prefix = "#";
            } else if (split[replace].startsWith("/")) {
                prefix = "/";
            } else if (split[replace].startsWith("'")) {
                prefix = "'";
            } else if (split[replace].startsWith("\"")) {
                prefix = "\"";
            } else if (split[replace].startsWith("$")) {
                prefix = "$";
            } else if (split[replace].startsWith("%")) {
                prefix = "%";
            } else if (split[replace].startsWith("@")) {
                prefix = "@";
            } else if (split[replace].startsWith("^")) {
                prefix = "^";
            } else if (split[replace].startsWith("*")) {
                prefix = "*";
            } else if (split[replace].startsWith("!")) {
                prefix = "!";
            } else if (split[replace].startsWith("&")) {
                prefix = "&";
            } else if (split[replace].startsWith("('")) {
                prefix = "('";
            } else if (split[replace].startsWith(".(")) {
                prefix = ".(";
            } else if (split[replace].startsWith("(")) {
                prefix = "(";
            } else if (split[replace].startsWith("+")) {
                prefix = "+";
            } else if (split[replace].startsWith("+=")) {
                prefix = "+=";
            } else if (split[replace].startsWith("`")) {
                prefix = "`";
            } else if (split[replace].startsWith("~")) {
                prefix = "~";
            } else if (split[replace].startsWith("|")) {
                prefix = "|";
            } else if (split[replace].startsWith("[")) {
                prefix = "[";
            } else if (split[replace].startsWith("{")) {
                prefix = "{";
            } else if (split[replace].startsWith("<")) {
                prefix = "<";
            } else if (split[replace].startsWith(".")) {
                prefix = ".";
            } else if (split[replace].startsWith("-")) {
                prefix = "-";
            } else if (split[replace].startsWith("_")) {
                prefix = "_";
            }
            sb.insert(0, prefix);


            split[replace] = sb.toString();
            return true;
        }
        return false;
    }

    private boolean isAWordWeDontReplace(String replace) {
        return replace.equalsIgnoreCase("is") || replace.equalsIgnoreCase("a") || replace.equalsIgnoreCase("the")
                || replace.equalsIgnoreCase("i") || replace.equalsIgnoreCase("my") || replace.equalsIgnoreCase("to")
                || replace.equalsIgnoreCase("in") || replace.equalsIgnoreCase("of") || replace.equalsIgnoreCase("butt")
                || replace.equalsIgnoreCase("and") || replace.equalsIgnoreCase("or") || replace.equalsIgnoreCase("your")
                || replace.equalsIgnoreCase("her") || replace.equalsIgnoreCase("his") || replace.equalsIgnoreCase("was")
                || replace.equalsIgnoreCase("lol") || replace.equalsIgnoreCase("wtf") || replace.equalsIgnoreCase("tbh")
                || replace.equalsIgnoreCase("haha") || replace.equalsIgnoreCase("it's") || replace.equalsIgnoreCase("for")
                || replace.equalsIgnoreCase("has") || replace.equalsIgnoreCase("if");
    }

    private void buttRetry(String[] split) {
        boolean success = replaceButt(split);
        if (!success) {
            replaceButt(split);
        }
    }

}
