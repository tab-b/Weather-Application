import random

words = ["test", "tester", "testest", "testerest"]

random = random.randrange(0, 3)   # finds a random number between 0 and 3 
chosen_word = words[random]  # uses the random number to choose the word to guess

choice = input("A random word has been chosen. It is " + str(len(chosen_word)) + " characters long.")

while len(chosen_word) != 0:
    choice = input()

    if chosen_word.find(choice) != -1:
        for x in choice:
            chosen_word = chosen_word.replace(x, "")
        print("Correct! You have " + str(len(chosen_word)) + " words left!")

    elif chosen_word.find(choice) == -1:
        print("Incorrect guess! You stil have " + str(len(chosen_word)) + " letters left.")


if len(chosen_word) == 0:
    print("You guessed the word! Congratulations!")


