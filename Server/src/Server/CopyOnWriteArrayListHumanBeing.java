package Server;

import FromTask.HumanBeing;

import java.util.*;

public class CopyOnWriteArrayListHumanBeing {
    public CopyOnWriteArrayListHumanBeing() {
        List<HumanBeing> humanBeingList = new LinkedList<HumanBeing>() {
            @Override
            public int size() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(java.lang.Object o) {
                return false;
            }

            @Override
            public Iterator<HumanBeing> iterator() {
                return null;
            }

            @Override
            public java.lang.Object[] toArray() {
                return new java.lang.Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(HumanBeing humanBeing) {
                return false;
            }

            @Override
            public boolean remove(java.lang.Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends HumanBeing> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends HumanBeing> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public HumanBeing get(int index) {
                return null;
            }

            @Override
            public HumanBeing set(int index, HumanBeing element) {
                return null;
            }

            @Override
            public void add(int index, HumanBeing element) {

            }

            @Override
            public HumanBeing remove(int index) {
                return null;
            }

            @Override
            public int indexOf(java.lang.Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(java.lang.Object o) {
                return 0;
            }

            @Override
            public ListIterator<HumanBeing> listIterator() {
                return null;
            }

            @Override
            public ListIterator<HumanBeing> listIterator(int index) {
                return null;
            }

            @Override
            public List<HumanBeing> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }
}
