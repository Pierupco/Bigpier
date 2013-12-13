package com.pier.support.util;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.junit.Assert;
import org.junit.Test;

public class TestJsonSerDe {

    @Test
    public void testSerDe() throws Exception {
        final TestObject testObject = new TestObject("123213", 213, 324.231);
        final JsonSerDe jsonSerDe = new JsonSerDe();
        final String json = jsonSerDe.serialize(testObject);
        final TestObject testObject2 = jsonSerDe.deserialize(json, TestObject.class);
        Assert.assertEquals(testObject, testObject2);
    }

    @Test(expected = JsonMappingException.class)
    public void testDeserializeEmptyJson() throws Exception {
        final JsonSerDe jsonSerDe = new JsonSerDe();
        jsonSerDe.deserialize("", TestObject.class);
    }

    @Test
    public void testDeserializeNull() throws Exception {
        final JsonSerDe jsonSerDe = new JsonSerDe();
        final TestObject testObject = jsonSerDe.deserialize("null", TestObject.class);
        Assert.assertNull(testObject);
    }

    @Test
    public void testParamed() throws Exception {
        final JsonSerDe jsonSerDe = new JsonSerDe();
        final NestedObject nestedObject = new NestedObject();
        nestedObject.intVal = 5;
        nestedObject.strVal = "";
        final ParamObject<NestedObject> paramObject = new ParamObject<NestedObject>();
        paramObject.setBody(nestedObject);
        System.out.println(jsonSerDe.serialize(paramObject));
    }

    public static class ParamObject<T> {
        private T body;

        public T getBody() {
            return body;
        }

        public void setBody(T body) {
            this.body = body;
        }
    }

    public static class NestedObject {
        private String strVal;
        private int intVal;

        public String getStrVal() {
            return strVal;
        }

        public void setStrVal(String strVal) {
            this.strVal = strVal;
        }

        public int getIntVal() {
            return intVal;
        }

        public void setIntVal(int intVal) {
            this.intVal = intVal;
        }
    }

    public static class TestObject {
        private String strValue;
        private int intValue;
        private double doubleValue;

        public TestObject() {
        }

        public TestObject(String strValue, int intValue, double doubleValue) {
            this.strValue = strValue;
            this.intValue = intValue;
            this.doubleValue = doubleValue;
        }

        public String getStrValue() {
            return strValue;
        }

        public void setStrValue(String strValue) {
            this.strValue = strValue;
        }

        public int getIntValue() {
            return intValue;
        }

        public void setIntValue(int intValue) {
            this.intValue = intValue;
        }

        public double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(double doubleValue) {
            this.doubleValue = doubleValue;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestObject that = (TestObject) o;

            if (Double.compare(that.doubleValue, doubleValue) != 0) return false;
            if (intValue != that.intValue) return false;
            if (strValue != null ? !strValue.equals(that.strValue) : that.strValue != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            result = strValue != null ? strValue.hashCode() : 0;
            result = 31 * result + intValue;
            temp = Double.doubleToLongBits(doubleValue);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }

}
