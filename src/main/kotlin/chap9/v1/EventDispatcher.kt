package chap9.v1

import chap8.MessageBus
import chap8.logger_v2.DomainLogger

class EventDispatcher {
    private val messageBus = MessageBus()
    private val domainLogger = DomainLogger()

    fun dispatch(events: List<IDomainEvent>) {

        events.forEach {
            dispatch(it)
        }

    }

    private fun dispatch(event: IDomainEvent) {

        when(event) {
            is EmailChangeEvent -> {
                messageBus.sendEmailChangedMessage(
                    event.userId,
                    event.newEmail
                )
            }

            is UserTypeChangedEvent -> {
                domainLogger.userTypeHasChanged(
                    event.userId,
                    event.oldType,
                    event.newType,
                )
            }
        }
    }
}