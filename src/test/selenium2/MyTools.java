package selenium2;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.nutz.lang.Lang;

public class MyTools {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int l = Lang.length(null);
		System.out.println("null:"+l);
		assertTrue(l==0);
		l = Lang.length(new ArrayList<>());
		System.out.println("list:"+l);
		assertTrue(l==0);
		l = Lang.length(new Object());
		System.out.println("object:"+l);
		assertTrue(l==1);
		l = Lang.length(new int[]{1,2,3});
		System.out.println("[]:"+l);
		assertTrue(l==3);
		
	}

}
