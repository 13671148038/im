package im;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaiyun.pojo.Task;
import com.kaiyun.pojo.TaskResult;

public class GsonTest {
	
	@Test
	public void methd1(){
		Gson g = new Gson();
		List<TaskResult> list = new ArrayList<>();
		TaskResult tr = new TaskResult();
		tr.setCheckResult("sdsd");
		tr.setTaskId("sdsdsd");
		tr.setUserName("lisi");
		list.add(tr);
		String tasks = g.toJson(list);
		Type listType = new TypeToken<List<TaskResult>>(){}.getType();
		List<TaskResult> taskList = (List<TaskResult>)g.fromJson(tasks,listType);
		TaskResult taskResult = taskList.get(0);
		System.out.println();
		
	}

}
