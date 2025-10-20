package linkedlist;

import support.CycleInfo;
import support.LLNode;

public class LinkedListCycleAnalyzer {
    public static <T> CycleInfo detectCycleInfo(LLNode<T> head) {
        if (head == null) return new CycleInfo(-1, 0); //guard clause
        // We'll use two pointers (slow and fast) to detect a cycle
        LLNode<T> slow = head;
        LLNode<T> fast = head;


        int cycleLength;  // how many nodes are in the cycle
        int entryIndex = 0;   // how far the cycle starts from the head


        // Move through the list until fast reaches the end or the two pointers meet
        while (fast != null && ((LLNode<T>) fast).getLink() != null) {
            slow = ((LLNode<T>) slow).getLink();           // slow moves one step
            fast = ((LLNode<T>) fast).getLink().getLink();      // fast moves two steps

            // If slow and fast meet, a cycle exists
            if (slow == fast) {
                LLNode<T> meet = slow;     // save the meeting point

                // Count how many nodes are in the cycle
                fast = fast.getLink();
                cycleLength = 1; // start at 1 because we already moved fast once
                while (fast != meet) {
                    fast = fast.getLink();
                    cycleLength++;
                }

                // Reset one pointer to the head
                slow = head;
                fast = head;

                // Move fast pointer ahead by the cycle length
                for (int i = 0; i < cycleLength; i++) {
                    fast = fast.getLink();
                }

                // Move both one step at a time until they meet
                // The number of steps taken is the entry index
                while (slow != fast) {
                    slow = slow.getLink();
                    fast = fast.getLink();
                    entryIndex++;
                }

                // Return both pieces of info: where the cycle starts, and how long it is
                return new CycleInfo(entryIndex, cycleLength);
            }
        }

        // If we reach here, there was no cycle
        return new CycleInfo(-1, 0);
    }
}
