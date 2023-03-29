package lab7.lb.domain.balancer

import lab7.entities.balancer.NodeAddress

interface LoadBalancer {
    /**
     * Получает ноду для клиента
     */
    fun getNode(): NodeAddress

    /**
     * Регистрирует ноду. Возвращает номер ноды
     */
    fun addNode(addr: NodeAddress): Int
}
