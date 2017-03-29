package tk.zielony.randomdata.person;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import tk.zielony.randomdata.DataContext;
import tk.zielony.randomdata.Generator;
import tk.zielony.randomdata.Matcher;

/**
 * Created by Marcin on 27.03.2017.
 */
public class StringFirstNameGenerator extends Generator<String> {
    private String[] female = {"Mary", "Patricia", "Linda", "Barbara", "Elizabeth", "Jennifer", "Maria", "Susan", "Margaret", "Dorothy", "Lisa", "Nancy", "Karen", "Betty", "Helen", "Sandra", "Donna", "Carol", "Ruth", "Sharon", "Michelle", "Laura", "Sarah", "Kimberly", "Deborah", "Jessica", "Shirley", "Cynthia", "Angela", "Melissa", "Brenda", "Amy", "Anna", "Rebecca", "Virginia", "Kathleen", "Pamela", "Martha", "Debra", "Amanda", "Stephanie", "Carolyn", "Christine", "Marie", "Janet", "Catherine", "Frances", "Ann", "Joyce", "Diane", "Alice", "Julie", "Heather", "Teresa", "Doris", "Gloria", "Evelyn", "Jean", "Cheryl", "Mildred", "Katherine", "Joan", "Ashley", "Judith", "Rose", "Janice", "Kelly", "Nicole", "Judy", "Christina", "Kathy", "Theresa", "Beverly", "Denise", "Tammy", "Irene", "Jane", "Lori", "Rachel", "Marilyn", "Andrea", "Kathryn", "Louise", "Sara", "Anne", "Jacqueline", "Wanda", "Bonnie", "Julia", "Ruby", "Lois", "Tina", "Phyllis", "Norma", "Paula", "Diana", "Annie", "Lillian", "Emily", "Robin"};
    private String[] male = {"James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas", "Christopher", "Daniel", "Paul", "Mark", "Donald", "George", "Kenneth", "Steven", "Edward", "Brian", "Ronald", "Anthony", "Kevin", "Jason", "Matthew", "Gary", "Timothy", "Jose", "Larry", "Jeffrey", "Frank", "Scott", "Eric", "Stephen", "Andrew", "Raymond", "Gregory", "Joshua", "Jerry", "Dennis", "Walter", "Patrick", "Peter", "Harold", "Douglas", "Henry", "Carl", "Arthur", "Ryan", "Roger", "Joe", "Juan", "Jack", "Albert", "Jonathan", "Justin", "Terry", "Gerald", "Keith", "Samuel", "Willie", "Ralph", "Lawrence", "Nicholas", "Roy", "Benjamin", "Bruce", "Brandon", "Adam", "Harry", "Fred", "Wayne", "Billy", "Steve", "Louis", "Jeremy", "Aaron", "Randy", "Howard", "Eugene", "Carlos", "Russell", "Bobby", "Victor", "Martin", "Ernest", "Phillip", "Todd", "Jesse", "Craig", "Alan", "Shawn", "Clarence", "Sean", "Philip", "Chris", "Johnny", "Earl", "Jimmy", "Antonio"};

    private Random random = new Random();
    private Gender gender;
    private boolean preventDuplicates;
    private boolean allowTwoNames;

    private Set<String> femaleUsed = new HashSet<>();
    private Set<String> maleUsed = new HashSet<>();

    public StringFirstNameGenerator() {
        this.gender = Gender.Both;
        preventDuplicates = true;
        allowTwoNames = true;
    }

    public StringFirstNameGenerator(Gender gender, boolean preventDuplicates, boolean allowTwoNames) {
        this.gender = gender;
        this.preventDuplicates = preventDuplicates;
        this.allowTwoNames = allowTwoNames;
    }

    @Override
    protected Matcher getDefaultMatcher() {
        return f -> f.getType().equals(String.class) && f.getName().contains("firstName");
    }

    @Override
    public String next(DataContext context) {
        Gender g = context.getGender();
        if (g == null)
            context.setGender(g = gender == Gender.Both ? random.nextBoolean() ? Gender.Female : Gender.Male : gender);
        String name;
        if (preventDuplicates) {
            if (g == Gender.Male && maleUsed.size() == male.length * (allowTwoNames ? male.length : 1))
                maleUsed.clear();
            if (g == Gender.Female && femaleUsed.size() == female.length * (allowTwoNames ? female.length : 1))
                femaleUsed.clear();
            do {
                name = next2(g);
            }
            while (g == Gender.Male && maleUsed.contains(name) || g == Gender.Female && femaleUsed.contains(name));
            if (g == Gender.Male) {
                maleUsed.add(name);
            } else {
                femaleUsed.add(name);
            }
        } else {
            name = next2(g);
        }
        return name;
    }

    private String next2(Gender g) {
        if (allowTwoNames && random.nextBoolean()) {
            String first, second;
            do {
                first = next(g);
                second = next(g);
            } while (first.equals(second));
            return first + " " + second;
        }
        return next(g);
    }

    private String next(Gender gender) {
        return gender == Gender.Female ? female[random.nextInt(female.length)] : male[random.nextInt(male.length)];
    }

    @Override
    public void reset() {
        femaleUsed.clear();
        maleUsed.clear();
    }

}
