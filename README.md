* `@ExtentWith(MockitoExtension.class)`: Use to add Mockito behaviour into JUnit5 lifecycle. Just an extension to add third-party vendors like Mockito for test classes.
* `@InjectMocks`: We use it to create mock instance. We use it with service layer.
* `@Mock`: If dependencies injected with `@InjectMocks` has some dependencies (such as repository layer). We use `@Mock` to create mock of those dependencies. It will also inject those dependencies.