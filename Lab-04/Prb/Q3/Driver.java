public class Driver{
    private static final TemplateFiller templateFiller = new TemplateFiller();

    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = {"name", "id"};
        String[] values = {"Heer", "H14"};
        String result = templateFiller.fillTemplate(template, names, values);
        System.out.println(result);
    }

    private static class TemplateFiller {
        public String fillTemplate(String template, String[] names, String[] values) {
            String result = template;
            for (int i = 0; i < names.length && i < values.length; i++) {
                result = result.replace("{" + names[i] + "}", values[i]);
            }
            return result;
        }
    }
}
