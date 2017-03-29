# RandomData

Your test/mock screens would look much better with some real data. RandomData generates random data and fills your POJOs with them.

Before you start, make sure to check out https://www.pexels.com/ - very nice site with free stock photos :)

![Screenshot](https://github.com/ZieIony/RandomData/blob/master/images/screenshot.png)

### Usage

Prepare a data class:

    public class User{
        Drawable avatar;
        String name, subtext, date;
    }

Setup RandomData with generators you need:

    RandomData randomData = new RandomData();
    randomData.addGenerators(new Generator[]{
        new StringNameGenerator(Gender.Both)),
        new TextGenerator().withMatcher(f -> f.getName().equals("subtext")),
        new DrawableAvatarGenerator(this),
        new StringDateGenerator()
    });

Fill your objects with data. There's no need to initialize arrays:

    User[] items = new User[10];
    randomData.fill(items);

### FAQ

##### 1. Where did you get these nice recycler rows from?

It's [Carbon](https://github.com/ZieIony/Carbon). RandomData helps me with carbon.components package.

##### 2. I need a generator you don't have

Open an issue and describe your generator. I'll try to add it.

##### 3. I've coded a generator. Can I make a pull request?

Sure thing!
