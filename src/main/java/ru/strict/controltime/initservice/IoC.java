package ru.strict.controltime.initservice;

import ru.strict.controltime.Main;
import ru.strict.controltime.view.manager.scheduler.ComputerWorkDurationScheduler;
import ru.strict.controltime.view.manager.scheduler.RefreshTimeManagerScheduler;
import ru.strict.ioc.InstanceType;
import ru.strict.ioc.SingletonIoC;
import ru.strict.util.ClassUtil;

public class IoC extends SingletonIoC {

    public static IoC instance() {
        if (getInstance() == null || !(getInstance() instanceof IoC)) {
            setInstance(new IoC());
        }
        return getInstance();
    }

    @Override
    protected void configure() {
        addSingleton("appPath", String.class, () -> ClassUtil.getPathByClass(Main.class));

        addComponent(EnvRegistryInit.class, InstanceType.CONFIGURATION);
        addComponent(JacksonObjectMapperInit.class, InstanceType.CONFIGURATION);
        addComponent(EventBrokerInit.class, InstanceType.CONFIGURATION);
        addComponent(EventPublishersInit.class, InstanceType.CONFIGURATION);
        addComponent(RepositoriesInit.class, InstanceType.CONFIGURATION);
        addComponent(PresentersInit.class, InstanceType.CONFIGURATION);
        addComponent(UseCasesInit.class, InstanceType.CONFIGURATION);
        addComponent(EventListenersInit.class, InstanceType.CONFIGURATION);
        addComponent(InnerControllerInit.class, InstanceType.CONFIGURATION);
        addComponent(ViewInit.class, InstanceType.CONFIGURATION);

        addComponent(ComputerWorkDurationScheduler.class);
        addComponent(RefreshTimeManagerScheduler.class);

        addComponent(RepositoriesInit.Start.class, InstanceType.CONFIGURATION);
        addComponent(ViewInit.Start.class, InstanceType.CONFIGURATION);
        addComponent(SchedulerInit.Start.class, InstanceType.CONFIGURATION);
    }
}
