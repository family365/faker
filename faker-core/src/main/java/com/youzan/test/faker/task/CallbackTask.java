package com.youzan.test.faker.task;

import org.springframework.stereotype.Component;

/**
 * Created by libaixian on 16/8/6.
 */
@Component
public class CallbackTask {
    public void addCallbackTask(String callbackURL, String callbackData, int delayTime) {
        //TODO: 需要把回调任务放到队列中, 队列的形式可以为task交给其他应用,或放入自己的队列中
    }
}
