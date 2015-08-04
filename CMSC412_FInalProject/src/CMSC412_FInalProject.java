//Benjamin_Knauth, CMSC412, Final Project, Alin Suciu, 10May2014.

import java.util.*;
import java.io.*;

public class CMSC412_FInalProject 
{
    public static void main(String[] args) 
    {        
        Scanner keyboard = new Scanner(System.in);
        String x = "";//Used to converts the int ArrayList into a String.
        int virtualMem = 10;//Used to generate random values between 0 - 9.
        int physicalMemSize = 5;//Virtually represents the number of physical address spaces, technically only helps differentiate between when all physical spaces are full or not.
        String stringLengthString;
        int stringLength = 0;//Used throughout to determine number of values in the reference string.
        String refStringValue;//Used for adding actual values to data structures.
        String userChoiceString;//Used to allow user to enter in non-random values.
        int userChoice = -1;
        int count;//Used when displaying the reference string, and reused in the data structures.
        ArrayList<Integer> refString = new ArrayList<Integer>();//Data structure utilized for FIFO algorithm.
        ArrayList<Integer> physicalMem2 = new ArrayList<Integer>();//Data structure utilized for OPT & LRU algorithms.
        LinkedList<Integer> physicalMem = new LinkedList<Integer>();//Data structure utilized for FIFO algorithm.
        Random randInt = new Random(virtualMem);//Used to generate random variables for reference string.        
        
        System.out.println("Welcome to Ben's Demand Paging virtual memory Simulator!");
        System.out.println("Please select from the following: ");
        
        do
        {
            x = refString.toString().replaceAll("^\\[|\\]$", "");//Used to convert reference string from int values to string to be displayed.
            
            System.out.print(
            "0 - Exit\n"
            + "1 - Read reference String\n"
            + "2 - Generate reference String\n"
            + "3 - Display current reference String\n"
            + "4 - Simulate FIFO\n"
            + "5 - Simulate OPT\n"
            + "6 - Simulate LRU\n"
            + ">> ");
            
            userChoiceString = keyboard.nextLine();            
            
            while(isInteger(userChoiceString) == false)
            {
                System.out.println("Input must be an integer.  Choose again>>");
                System.out.print(">> ");
                userChoiceString = keyboard.nextLine(); 
            }
            
            userChoice = Integer.parseInt(userChoiceString);
            
            System.out.println();
            
            if(userChoice == 0)//Exits Program.
                System.out.println("Goodbye!");
            
                else if(userChoice == 1)//Allows user to enter values into the reference string.
                {
                    stringLength = 0;
                    System.out.print("Enter the length of the reference String: ");
                    stringLengthString = keyboard.nextLine();                    
                    
                    while(stringLength == 0)
                    {
                        if(isInteger(stringLengthString) == false)
                        {
                            System.out.println("String length must be an integer.");
                            System.out.print("Try again>> ");
                            stringLengthString = keyboard.nextLine();
                        }
                        else
                        {
                            stringLength = Integer.parseInt(stringLengthString);                            
                        }
                    }
                    
                    System.out.println();
                    
                    count = 0;                    
                    refString.clear();
                    
                    while(count < stringLength)
                    {                        
                        System.out.print("Enter reference string value " + (count + 1) + ": ");
                        refStringValue = keyboard.nextLine();
                        
                        if(isInteger(refStringValue) == false)
                        {
                            System.out.println("Input must be an integer.");
                        }
                        else if(Integer.parseInt(refStringValue) < 0 || Integer.parseInt(refStringValue) > 9)
                        {
                            System.out.println("Virtual Memory has 10 frames(value must be between 0 & 9.");
                        }
                        else
                        {
                            refString.add(Integer.parseInt(refStringValue));
                            count++;
                        }
                    }   
                    
                        System.out.println();
                }
            
                else if(userChoice == 2)//Randomly Generates values for the reference string.
                {
                    stringLength = 0;
                    System.out.print("Enter the length of the reference String: ");
                    stringLengthString = keyboard.nextLine();
                    
                    while(stringLength == 0)
                    {
                        if(isInteger(stringLengthString) == false)
                        {
                            System.out.println("String length must be an integer.");
                            System.out.print("Try again>> ");
                            stringLengthString = keyboard.nextLine();
                        }
                        else
                        {
                            stringLength = Integer.parseInt(stringLengthString);                            
                        }
                    }
                    
                    System.out.println();
                    
                    count = 0;                    
                    refString.clear();
                    
                    while(count < stringLength)
                    {                    
                        refString.add(randInt.nextInt(10));
                        count++;
                    }
                }
            
                else if(userChoice == 3)//Displays the reference string.
                {
                    
                    if(refString.isEmpty())                    
                        System.out.println("The reference string is empty.");                    
                    else
                        System.out.println(x);
                    
                    System.out.println();
                }
            
                else if(userChoice == 4)//FIFO algorithm.
                {
                    int enter = 0;
                    physicalMem.clear();
                    if(refString.isEmpty())
                        System.out.println("The reference String is empty.");
                    else
                    {
                        for(int i = 0; i < stringLength; i++)
                        {
                            System.out.println(x);
                            System.out.println("Reference String value: " + refString.get(i));
                            if(!physicalMem.contains(refString.get(i)))//Checks to see if page already exists in physical memory.
                            {
                                if(physicalMem.size() < physicalMemSize)//Fills in null physical frames.
                                    physicalMem.addFirst(refString.get(i));
                                else//Adds and removes pages from frames in basic FIFO style.
                                {
                                    physicalMem.addFirst(refString.get(i));
                                    physicalMem.removeLast();
                                }
                            }
                            
                            for(int j = 0; j < physicalMemSize; j++)
                            {   
                                if(j <= (physicalMem.size() - 1))
                                    System.out.println("Physical Frame " + j + ": " + physicalMem.get(j));
                                else
                                    System.out.println("Physical Frame " + j + ": null");
                            }
                            
                            if(enter == 1)
                            {    
                                System.out.print("Press the Enter key to continue");
                                keyboard.nextLine();
                            }
                            else
                            {
                                System.out.print("Press the Enter key to continue");
                                keyboard.nextLine();
                                keyboard.nextLine();
                                enter++;
                            }                           
                            
                            System.out.println();                            
                        }
                    }
                    
                    System.out.println();                        
                }
            
                else if(userChoice == 5)//OPT algorithm.
                {
                    int enter = 0;                    
                    int k = 0;
                    int flag = 0;
                    physicalMem2.clear();
                    if(refString.isEmpty())
                        System.out.println("The reference String is empty.");
                    else
                    {
                        for(int i = 0; i < stringLength; i++)
                        {
                            System.out.println(x);
                            System.out.println("Reference String value: " + refString.get(i));
                            if(!physicalMem2.contains(refString.get(i)))//Checks to see if page already exists in physical memory.
                            {
                                if(physicalMem2.size() < physicalMemSize)//Fills in null physical frames.
                                    physicalMem2.add(refString.get(i));
                                else//OPT search and assignment of pages.
                                {
                                    
                                    int largestCount = 0;                                    
                                    for(int j = 0; j < physicalMem2.size(); j++)//Cycles through physical memory spaces to determine which should be replaces.
                                    {
                                        count = 0;
                                        k = i;
                                        while(physicalMem2.get(j) != refString.get(k) && k < (stringLength - 1))//Cycles through reference variables to count how far same page is located.
                                        {
                                            count++;
                                            k++;
                                        }
                                        
                                        if(count > largestCount)//Flags the physical memory variable most distant in the reference string.
                                        {
                                            largestCount = count;
                                            flag = j;
                                        }
                                    }
                                    
                                    physicalMem2.set(flag, refString.get(i));//Replaces flagged physical memory variable value with current value in reference string.
                                }
                            }
                            
                            for(int l = 0; l < physicalMemSize; l++)
                            {   
                                if(l <= (physicalMem2.size() - 1))
                                    System.out.println("Physical Frame " + l + ": " + physicalMem2.get(l));
                                else
                                    System.out.println("Physical Frame " + l + ": null");
                            }
                            
                            if(enter == 1)
                            {    
                                System.out.print("Press the Enter key to continue");
                                keyboard.nextLine();
                            }
                            else
                            {
                                System.out.print("Press the Enter key to continue");
                                keyboard.nextLine();
                                keyboard.nextLine();
                                enter++;
                            }                           
                            
                            System.out.println();
                        }
                    }
                }
            
                else if(userChoice == 6)//LRU algorithm.
                {
                    int enter = 0;                    
                    int k = 0;
                    int flag = 0;
                    physicalMem2.clear();
                    if(refString.isEmpty())
                        System.out.println("The reference String is empty.");
                    else
                    {
                        for(int i = 0; i < stringLength; i++)
                        {
                            System.out.println(x);
                            System.out.println("Reference String value: " + refString.get(i));
                            if(!physicalMem2.contains(refString.get(i)))//Checks to see if page already exists in physical memory.
                            {
                                if(physicalMem2.size() < physicalMemSize)
                                    physicalMem2.add(refString.get(i));
                                else//LRU search and assignment of pages.
                                {
                                    
                                    int largestCount = 0;                                    
                                    for(int j = 0; j < physicalMem2.size(); j++)//Cycles through physical memory spaces to determine which should be replaces.
                                    {
                                        count = 0;
                                        k = i;
                                        while(physicalMem2.get(j) != refString.get(k) && k > -1)//Cycles through reference variables to count how far same page is located.
                                        {
                                            count++;
                                            k--;
                                        }
                                        
                                        if(count > largestCount)//Flags the physical memory variable most distant in the reference string.
                                        {
                                            largestCount = count;
                                            flag = j;
                                        }
                                    }
                                    
                                    physicalMem2.set(flag, refString.get(i));//Replaces flagged physical memory variable value with current value in reference string.
                                }
                            }
                            
                            for(int l = 0; l < physicalMemSize; l++)
                            {   
                                if(l <= (physicalMem2.size() - 1))
                                    System.out.println("Physical Frame " + l + ": " + physicalMem2.get(l));
                                else
                                    System.out.println("Physical Frame " + l + ": null");
                            }
                            
                            if(enter == 1)
                            {    
                                System.out.print("Press the Enter key to continue");
                                keyboard.nextLine();
                            }
                            else
                            {
                                System.out.print("Press the Enter key to continue");
                                keyboard.nextLine();
                                keyboard.nextLine();
                                enter++;
                            }                           
                            
                            System.out.println();
                        }
                    }
                }
            
                else
                    System.out.println("That is an invalid choice.  Try again.");            
        }
        while(userChoice != 0); 
    }
    
    public static boolean isInteger(String value)
    {
        try
        {
            Integer.parseInt(value);
        }catch(NumberFormatException e)
         {
             return false;
         }
        return true;
    }
}
