    package com.diego.desafio_goat_b.repository;
    
    import com.diego.desafio_goat_b.domain.entity.Supplier;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    
    import java.util.List;
    import java.util.UUID;
    
    public interface SupplierRepository extends JpaRepository<Supplier, UUID> {
        List<Supplier> findByNameContainingIgnoreCase(String name);
    
        boolean existsByTaxId(String taxId);
    
        @Query("""
                    select s
                    from Supplier s
                    join fetch s.createdBy cb
                    where cb.id = :creatorId
                """)
        List<Supplier> findAllWithCreatorByCreatorId(@Param("creatorId") UUID creatorId);
    
    }
    
