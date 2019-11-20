package tk.zielony.randomdata.common;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

public class TextGenerator extends Generator<String> {
    private static String[] loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin nibh augue, suscipit a, scelerisque sed, lacinia in, mi. Cras vel lorem. Etiam pellentesque aliquet tellus. Phasellus pharetra nulla ac diam. Quisque semper justo at risus. Donec venenatis, turpis vel hendrerit interdum, dui ligula ultricies purus, sed posuere libero dui id orci. Nam congue, pede vitae dapibus aliquet, elit magna vulputate arcu, vel tempus metus leo non est. Etiam sit amet lectus quis est congue mollis. Phasellus congue lacus eget neque. Phasellus ornare, ante vitae consectetuer consequat, purus sapien ultricies dolor, et mollis pede metus eget nisi. Praesent sodales velit quis augue. Cras suscipit, urna at aliquam rhoncus, urna quam viverra nisi, in interdum massa nibh nec erat. ".split("\\. ");
    private int sentences;
    private boolean useSequence;
    private int sequence;

    public TextGenerator() {
        sentences = 1;
        useSequence = true;
    }

    public TextGenerator(int sentences, boolean useSequence) {
        this.sentences = sentences;
        this.useSequence = useSequence;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getName() != null && (f.getName().contains("description") || f.getName().contains("text"));
    }

    @Override
    public String next(DataContext context) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < sentences; i++) {
            builder.append(loremIpsum[(i + sequence) % loremIpsum.length]).append(". ");
            if (i % loremIpsum.length == loremIpsum.length - 1 && sentences > loremIpsum.length)
                builder.append("\n\n");
        }
        if (useSequence)
            sequence += sentences;
        return builder.toString();
    }

    @Override
    public void reset() {
        sequence = 0;
    }
}
