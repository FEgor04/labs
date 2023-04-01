package lab8.lb.domain.usecase

import lab8.entities.CollectionInfo
import lab8.entities.balancer.NodeAddress
import lab8.entities.vehicle.Vehicle
import lab8.entities.vehicle.VehicleType
import lab8.lb.domain.balancer.LoadBalancer
import lab8.lb.domain.cache.Cache
import lab8.lb.domain.synchornizer.CacheSynchronizer

class BalancerUseCase(
    private val balancer: LoadBalancer,
    private val cache: Cache,
    private val synchronized: CacheSynchronizer,
) {
    fun getNode() = balancer.getNode()
    fun addNode(address: NodeAddress): Int = balancer.addNode(address)
    fun show(): List<Vehicle> = cache.show()
    fun countByType(type: VehicleType?): Int = cache.countByType(type)
    fun countLessThanEnginePower(power: Double): Int = cache.countLessThanEnginePower(power)
    fun info(): CollectionInfo = cache.info()
    fun getMinByID(): Vehicle? = cache.getMinByID()

    suspend fun synchronize() {
        val vehicles = synchronized.synchronize()
        cache.synchronize(vehicles)
    }
}
