package lab8.lb.domain.balancer

import lab8.entities.balancer.NodeAddress

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
