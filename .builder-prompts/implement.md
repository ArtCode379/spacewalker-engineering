You are running the implementation phase for one Openclaw Android app.

Use these orchestrator instructions as binding context: /home/codex-agent/codex-app-agent/AGENTS.md
Use this screen spec: /home/codex-agent/codex-app-agent/screens-service.md
Project directory: /tmp/spacewalker-engineering

Task metadata:
- Asana task gid: 1217012259084557
- Asana task name: GB GW3 C346
- Asana URL: https://app.asana.com/1/1208304498069546/project/1211782465256228/task/1217012259084557
- App name: Spacewalker Engineering
- Company: SPACEWALKER CONSULTING LTD
- Domain: http://spacewalker.digital/
- Package: spacewalkerconsulting.engineering.spacewalkerhub
- Prefix: PBUHC
- Type: service
- Description: Специфика компании — консультирование в области инженерного проектирования, научно-техническое сопровождение проектов и технический аудит систем.
Приложение по предложению услуг компании содержит:
Каталог услуг и решений: список всех направлений технического консалтинга (с возможностью сортировки по категориям: например, «Промышленный инжиниринг», «Техническая экспертиза», «Энергоаудит и экологический консалтинг»).
Портфолио (Галерея): демонстрация успешно реализованных инженерных решений и завершенных технических проектов.
База знаний: страница с экспертными научно-техническими статьями по теме инноваций в инженерии, стандартизации или материаловедения (минимум 3 статьи).
Логика взаимодействия:
Бронирование консультации: страница записи на первичный технический осмотр или экспертную консультацию по проекту с формой (открывается со страницы деталей услуги по кнопке «Забронировать консультацию»).
Подтверждение: после подтверждения бронирования пользователь видит баннер с информацией о номере и деталях сессии, а также уведомление о том, что инженер-консультант будет ожидать его в онлайн-конференции или по адресу объекта в назначенное время.
Настройки приложения содержат:
Название компании.
Версию приложения.
Раздел Customer Support со ссылкой на сайт компании.

Do Phase 2 and Phase 3 only:
1. Extract or derive the style guide.
2. Do not create project-local agent instruction files inside /tmp/spacewalker-engineering.
3. Implement all required screens/content/data/assets/icon according to the orchestrator AGENTS.md and the screen spec.
4. Icon generation is best-effort: if Leonardo/imagegen cannot provide a filesystem-backed icon quickly, continue implementing the app with existing assets.
5. Do not push to GitHub, do not update Asana, and do not send Slack.
6. You may run local checks while implementing, but the runner will run quality/build afterward.
7. Keep every Kotlin file conventionally formatted: one statement per line, annotations above declarations, expanded indented Compose blocks, no semicolon-compressed code, and no source line longer than 200 characters.
