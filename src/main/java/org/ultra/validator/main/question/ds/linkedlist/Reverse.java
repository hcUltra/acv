package org.ultra.validator.main.question.ds.linkedlist;

import org.ultra.validator.annotation.CorrectMethod;
import org.ultra.validator.annotation.Validator;
import org.ultra.validator.annotation.ValidatorMethod;
import org.ultra.validator.common.util.ListNode;
import org.ultra.validator.process.Active;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hcUltra
 * @description
 * @date 2024/5/2 13:15
 **/
@Validator
public class Reverse {
    @ValidatorMethod
    public static ListNode<Integer> reverseList(ListNode<Integer> head) {
        ListNode<Integer> prev = null;
        ListNode<Integer> next = null;
        while (head != null) {
            next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    @CorrectMethod
    public static ListNode<Integer> correct(ListNode<Integer> head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = correct(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {
        List<String> constraints = new ArrayList<>();
        constraints.add("0 <= acv000.size <= 5000");
        constraints.add("0 <= acv010.value <= 5000");
        new Active().activateValidator(constraints);
    }
}