package seleniumStart;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class retry implements IRetryAnalyzer {

	int count = 1;
	int maxcount = 3; //3

	// -
	@Override
	public boolean retry(ITestResult result)
	{

		if (count < maxcount)  // 1  < 3  //  2<3  // 
		{
			System.out.println("retrying");
			count++;
			return true; // Retry test
		}

		return false;
	}

}
