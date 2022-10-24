import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.lang.Math;


public class A_Star {
    private static int N = 0;

    public static String[] createInitialSituation(int N) {
        int whiteCounter = 0;
        int blackCounter = 0;
        int blankCounter = 0;
        int value;
        String[] situation = new String[2*N + 1];
        Random generator = new Random();

        for(int i = 0; i < situation.length; i++) {
            value = generator.nextInt(3); {
                if(value == 0) {
                    if(whiteCounter < N) {
                        situation[i] = "A";
                        whiteCounter++;
                    }
                    else {
                        value = generator.nextInt(2); 
                        if(value == 0) {
                            if(blackCounter < N) {
                                situation[i] = "M";
                                blackCounter++;
                            }
                            else {
                                if(blankCounter == 0) {
                                    situation[i] = "_";
                                    blankCounter++;
                                }
                            }
                        }
                        else {
                            if(blankCounter == 0) {
                                situation[i] = "_";
                                blankCounter++;
                            }
                            else{
                                if(blackCounter < N) {
                                    situation[i] = "M";
                                    blackCounter++;
                                }
                            }
                        }
                    }
                }
                else if(value == 1) {
                    if(blackCounter < N) {
                        situation[i] = "M";
                        blackCounter++;
                    }
                    else {
                        value = generator.nextInt(2); 
                        if(value == 0) {
                            if(whiteCounter < N) {
                                situation[i] = "A";
                                whiteCounter++;
                            }
                            else {
                                if(blankCounter == 0) {
                                    situation[i] = "_";
                                    blankCounter++;
                                }
                            }
                        }
                        else {
                            if(blankCounter == 0) {
                                situation[i] = "_";
                                blankCounter++;
                            }
                            else{
                                if(whiteCounter < N) {
                                    situation[i] = "A";
                                    whiteCounter++;
                                }
                            }
                        }
                    }
                }
                else {
                    if(blankCounter == 0) {
                        situation[i] = "_";
                        blankCounter++;
                    }
                    else {
                        value = generator.nextInt(2); 
                        if(value == 0) {
                            if(whiteCounter < N) {
                                situation[i] = "A";
                                whiteCounter++;
                            }
                            else {
                                if(blackCounter < N) {
                                    situation[i] = "M";
                                    blackCounter++;
                                }
                            }
                        }
                        else {
                            if(blackCounter < N) {
                                situation[i] = "M";
                                blackCounter++;
                            }
                            else{
                                if(whiteCounter < N) {
                                    situation[i] = "A";
                                    whiteCounter++;
                                }
                            }
                        }
                    }
                }
            }
        }
        return situation;
    }

    public static ArrayList<situationNode> findChildren(situationNode node) {
        String[] parentSituation = node.getSituation();
        ArrayList<situationNode> children = new ArrayList<situationNode>();
        ArrayList<String[]> grandparentSituations = new ArrayList<String[]>();
        situationNode grandparent = node.getParent();
        int parentDepth = node.getDepth();
        int blankPos = 0;

        while(grandparent != null) {
            grandparentSituations.add(grandparent.getSituation());
            grandparent = grandparent.getParent();
        }

        for(int i = 0; i < parentSituation.length; i++) {
            if(parentSituation[i].equals("_")) {
                blankPos = i;
            }
        }
        
        for(int i = 0; i < parentSituation.length; i++) {
            int cost = Math.abs(i - blankPos);
            if(cost == 0) {
                continue;
            }

            if(cost <= N) {
                String[] childSituation = new String[parentSituation.length];
                childSituation = Arrays.copyOf(parentSituation, parentSituation.length);
                String temp = childSituation[i];
                childSituation[i] = childSituation[blankPos];
                childSituation[blankPos] = temp;
                boolean cycle = false;

                for(int j = 0; j < grandparentSituations.size(); j++) {
                    if(childSituation == grandparentSituations.get(j)) {
                        cycle = true;
                        break;
                    }
                }

                if(!cycle) {
                    int childDepth = parentDepth + cost;
                    situationNode childNode = new situationNode(childDepth, childSituation);
                    childNode.setParent(node);
                    children.add(childNode);
                }
            }
        }

        return children;
    }

    public static ArrayList<situationNode> findChildrenWithoutRestrictions(situationNode node) {
        String[] parentSituation = node.getSituation();
        ArrayList<situationNode> children = new ArrayList<situationNode>();
        ArrayList<String[]> grandparentSituations = new ArrayList<String[]>();
        situationNode grandparent = node.getParent();
        int parentHcost = node.getHcost();
        int blankPos = 0;

        while(grandparent != null) {
            grandparentSituations.add(grandparent.getSituation());
            grandparent = grandparent.getParent();
        }

        for(int i = 0; i < parentSituation.length; i++) {
            if(parentSituation[i].equals("_")) {
                blankPos = i;
            }
        }
        
        for(int i = 0; i < parentSituation.length; i++) {
            int cost = Math.abs(i - blankPos);
            if(cost == 0) {
                continue;
            }

            String[] childSituation = new String[parentSituation.length];
            childSituation = Arrays.copyOf(parentSituation, parentSituation.length);
            String temp = childSituation[i];
            childSituation[i] = childSituation[blankPos];
            childSituation[blankPos] = temp;
            boolean cycle = false;

            for(int j = 0; j < grandparentSituations.size(); j++) {
                if(childSituation == grandparentSituations.get(j)) {
                    cycle = true;
                    break;
                }
            }

            if(!cycle) {
                int childHcost = parentHcost + cost;
                situationNode childNode = new situationNode(0, childSituation);
                childNode.setParent(node);
                childNode.setHcost(childHcost);
                children.add(childNode);
            }
        }

        return children;
    }

    public static boolean checkIfFinal(String[] situation) {
        int blackCounter = 0;
        int pos = 0;

        if(!situation[situation.length - 1].equals("A")) {
            return false;
        }

        while(blackCounter < N) {
            if(situation[pos].equals("A")) {
                return false;
            }

            if(situation[pos].equals("M")) {
                blackCounter++;
            }

            pos++;
        }

        return true;
    }

    public static boolean checkIfFinalWithoutRestrictions(String[] situation) {
        int blackCounter = 0;
        int pos = 0;

        while(blackCounter < N) {
            if(situation[pos].equals("A")) {
                return false;
            }

            if(situation[pos].equals("M")) {
                blackCounter++;
            }

            pos++;
        }

        return true;
    }

    public static void printSolution(situationNode solution) {
        ArrayList<situationNode> path = new ArrayList<situationNode>();
        path.add(solution);
        situationNode parent = solution.getParent();

        while(parent != null) {
            path.add(0, parent);
            parent = parent.getParent();
        }

        for(situationNode node : path) {
            node.show();
            System.out.println(" ->");
        }

        System.out.println("Done!");
    }

    public static int UCS_Without_Restrictions(situationNode initialSituationNode) {
        ArrayList<situationNode> searchFrontier = new ArrayList<situationNode>();
        ArrayList<situationNode> solutions = new ArrayList<situationNode>();
        String[] initialSituation = initialSituationNode.getSituation();
        initialSituationNode.setHcost(0);
        searchFrontier.add(initialSituationNode);

        while(!searchFrontier.isEmpty()) {
            situationNode node = searchFrontier.get(0);

            if(!solutions.isEmpty()) {
                int lowestCost = 9999;

                for(situationNode solution : solutions) {
                    if(solution.getHcost() < lowestCost) {
                        lowestCost = solution.getHcost();
                    }
                }

                if(node.getHcost() >= lowestCost) {
                    break;
                }
            }

            if(checkIfFinalWithoutRestrictions(node.getSituation())) {
                solutions.add(node);
                continue;
            }

            ArrayList<situationNode> children = findChildrenWithoutRestrictions(node);

            for(situationNode child : children) {
                for(int i = 0; i < searchFrontier.size(); i++) {
                    if((child.getHcost()) <= (searchFrontier.get(i).getHcost())) {
                        searchFrontier.add(i, child);
                        break;
                    }

                    if(i == searchFrontier.size() - 1) {
                        searchFrontier.add(child);
                    }
                }
            }
            searchFrontier.remove(node);
        }
        
        ArrayList<situationNode> emptyLst = new ArrayList<situationNode>();
        initialSituationNode.setChildren(emptyLst);
        return solutions.get(0).getDepth();
    }

    public static void main(String[] args) {
        Scanner scannerForN = new Scanner(System.in);
        System.out.println("Please enter N : ");
        N = scannerForN.nextInt();
        System.out.println("Please enter the initial situation : ");
        Scanner scannerForSituation = new Scanner(System.in);
        String[] initialSituation = scannerForSituation.nextLine().split("");
        ArrayList<situationNode> searchFrontier = new ArrayList<situationNode>();
        ArrayList<situationNode> solutions = new ArrayList<situationNode>();
        situationNode initialSituationNode = new situationNode(0, initialSituation);
        searchFrontier.add(initialSituationNode);
        System.out.print("Initial situation: ");
        initialSituationNode.show();
        System.out.println("");

        while(!searchFrontier.isEmpty()) {
            situationNode node = searchFrontier.get(0);

            if(!solutions.isEmpty()) {
                int lowestCost = 9999;

                for(situationNode solution : solutions) {
                    if(solution.getDepth() < lowestCost) {
                        lowestCost = solution.getDepth();
                    }
                }

                if(node.getDepth() >= lowestCost) {
                    break;
                }
            }

            if(checkIfFinal(node.getSituation())) {
                solutions.add(node);
                continue;
            }

            ArrayList<situationNode> children = findChildren(node);
            for(situationNode child : children) {
                child.setH(UCS_Without_Restrictions(child));
            }

            for(situationNode child : children) {
                for(int i = 0; i < searchFrontier.size(); i++) {
                    if((child.getDepth() + child.getH()) <= (searchFrontier.get(i).getDepth() + searchFrontier.get(i).getH())) {
                        searchFrontier.add(i, child);
                        break;
                    }

                    if(i == searchFrontier.size() - 1) {
                        searchFrontier.add(child);
                    }
                }
            }
            searchFrontier.remove(node);
        }

        for(situationNode solution : solutions) {
            printSolution(solution);
        }
    }
}