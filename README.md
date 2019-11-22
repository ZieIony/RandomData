# RandomData

Your test/mock screens would look much better with some real data. RandomData automatically fills your structures with generated, random names, numbers, images, etc. RandomData uses data libraries, validating algorithms and keeps track of a context to make sure that every bit of generated data is consistent and makes sense. Did you notice that a female name always comes with a picture of a woman? :)

Before you start, make sure to check out https://www.pexels.com/ - very nice site with free stock photos :)

![Screenshot](https://github.com/ZieIony/RandomData/blob/master/images/screenshot.png)
![Screenshot2](https://github.com/ZieIony/RandomData/blob/master/images/screenshot2.png)

### Setup

Visit [Jitpack](https://jitpack.io/#zieiony/randomdata) for installation instructions.
    
Add RandomData to your project's dependencies. Select only modules you need (because of their size):
    
    dependencies {
        implementation 'com.github.ZieIony.RandomData:person:-SNAPSHOT'
    }

### Usage

Prepare a data class:

```Java
public class User{
    Drawable avatar;
    String name, subtext, date;
    
    @Ignore
    Parcelable parcelable;  // don't fill this field
    
    final static int TYPE_ID = 5;   // ignored automatically
}
```

Constructor parameters don't have names on Java below 1.8. Use `RandomValue` annotation to provide names.

```Kotlin
data class CreditCardItem(
        @RandomValue(name = "name")
        val name: String,
        @RandomValue(name = "number")
        val number: String,
        @RandomValue(name = "amount")
        val amount: String,
        @RandomValue(name = "image")
        val image: Drawable,
        @RandomValue(name = "validity")
        val validity: Validity
) : Serializable
```

Setup RandomData with generators you need:

```Java
randomData = new RandomData();
randomData.addGenerator(String.class, new StringNameGenerator(Gender.Both).withMatcher(f -> f.getName().equals("text") && f.getDeclaringClass().equals(DefaultAvatarTextSubtextDateItem.class) || f.getName().equals("name")));
randomData.addGenerator(String.class, new TextGenerator().withMatcher(f -> f.getName().equals("subtext")));
randomData.addGenerator(Drawable.class, new DrawableAvatarGenerator(this));
randomData.addGenerator(String.class, new DateGenerator().withTransformer(new DateToStringTransformer()));
randomData.addGenerator(String.class, new StringCardNumberGenerator());
randomData.addGenerator(String.class, new StringAmountGenerator());
randomData.addGenerator(Drawable.class, new DrawableImageGenerator(this).withMatcher(f -> f.getName().equals("image")));
randomData.addGenerator(Validity.class, new EnumGenerator<>(Validity.class));
randomData.addGenerator(Float.class, new FloatGenerator());
```

Fill or generate your objects with data. There's no need to initialize arrays:

```Java
User[] items = new User[10];
randomData.fill(items);

CreditCardItem creditCard = randomData.generate(CreditCardItem.class);
```

### Modules

##### Common

 - picture
 - date
 - id
 - hash
 - lorem ipsum

##### Person

 - avatar
 - name
 - email
 - gender
 - phone number
 - age
 
##### Finance

 - amount
 - card number
 
### FAQ

##### 1. Where did you get these nice recycler rows from?

It's [Carbon](https://github.com/ZieIony/Carbon). RandomData helps me with carbon.components package.

##### 2. I need a generator you don't have

Open an issue and describe your generator. I'll try to add it.

##### 3. I've coded a generator. Can I make a pull request?

Sure thing!
