# DailyQuizApp

Это мобильное приложение для прохождения коротких викторин на различные темы. Можете проверить свои знания, отслеживать прогресс и улучшать результаты с каждой попыткой!

## 📖 Содержание

[✨ Возможности](#-возможности) \
[📸 Скриншоты](#-скриншоты) \
[🛠 Технологии и библиотеки](#-технологии-и-библиотеки) \
[🚀 Основные компоненты](#-основные-компоненты) \
[📁 Структура проекта](#-структура-проекта) 

## ✨ Возможности

🧠 Прохождение викторины из 5 \
🎛️ Выбор категории и сложности вопросов перед началом викторины \
📊 Отображение истории прохождений \
🗑️ Удаление истории \
🔍 Детальный разбор викторины \
⏱️ Ограничение по времени на прохождение

## 📸 Скриншоты

## 🛠 Технологии и библиотеки

🅺 Язык: Kotlin \
🚀Минимальная версия SDK: API 26 (Android 8.0) \
🏗 Архитектура: MVVM \
💾 База данных: Room Persistence Library \
⚡ Асинхронность: Kotlin Coroutines, Flow \
🌐 Сеть: Retrofit, OkHttp \
🗺️ Навигация: Jetpack Navigation Component \
🎨 UI: Android Views (XML Layouts) 

## 🚀 Основные компоненты

### Экран викторины
- Начальное состояние: Приветствие, кнопка запуска и истории
- Состояние загрузки: Индикатор загрузки вопросов
- Прохождение викторины: 5 вопросов с вариантами ответов
- Завершение: Показ результатов

### Экран истории
- Список попыток: Дата, результат, категория и сложность
- Контекстное меню: Удаление попыток долгим нажатием
- Пустое состояние: Сообщение при отсутствии попыток

### Экран разбора викторины
- Статистика: Общий результат и время прохождения
- Разбор каждого вопроса: Текст вопроса и варианты ответов
- Визуальная индикация правильности
- Категория и сложность: Отображение параметров викторины
- Навигация: Возможность начать новую викторину

### Экран выбора сложности и категори
- Выбор категории: Выпадающий список с темами
- Выбор сложности: Выпадающий список с уровнями
- Кнопка запуска: "Начать викторину" (активна после выбора параметров)

## 📁 Структура проекта

``` bash
com.nastya.dailyquiz/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   │   └── HistoryDao.kt
│   │   ├── database/
│   │   │   ├── HistoryDatabase.kt
│   │   │   └── TypeConverter.kt
│   │   ├── entity/
│   │   │   └── History.kt
│   │   └── mapper/
│   │       └── QuestionMapper.kt
│   ├── remote/
│   │   ├── api/
│   │   │   └── QuestionsService.kt
│   │   └── model/
│   │       ├── ApiQuestion.kt
│   │       └── ApiResponse.kt
│   └── repository/
│       └── QuestionRepository.kt
├── domain/
│   └── model/
│       └── QuizQuestion.kt
└── ui/
    ├── start/
    │   ├── StartFragment.kt
    │   ├── StartViewModel.kt
    │   └── StartViewModelFactory.kt
    ├── filter/
    │   ├── FilterQuizFragment.kt
    │   └── FilterQuizViewModel.kt
    ├── load/
    │   ├── LoadFragment.kt
    │   └── LoadViewModel.kt
    ├── quiz/
    │   ├── QuestionFragment.kt
    │   └── QuestionViewModel.kt
    ├── result/
    │   ├── ResultFragment.kt
    │   ├── ResultViewModel.kt
    │   ├── ResultItemAdapter.kt
    │   └── ResultViewModelFactory.kt
    ├── history/
    │   ├── HistoryItemAdapter.kt
    │   ├── HistoryFragment.kt
    │   ├── BlankHistoryFragment.kt
    │   ├── HistoryViewModel.kt
    │   ├── HistoryViewModelFactory.kt
    │    └── HistoryDiffItemCallback.kt
    └── main/
        └── MainActivity.kt
```