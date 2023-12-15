package com.mukul.triply.common.baseclasses;

import lombok.NonNull;
import org.apache.coyote.BadRequestException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

import static com.mukul.triply.constant.Constants.MAX_PAGE_FETCH_LIMIT;

public abstract class BaseService<T extends BaseEntity, R extends BaseEntry> {

    protected BaseRepository<T> repository;

    protected BaseMapper<R, T> mapper;

    public R create(@NonNull final R entry) {

        final T entity = mapper.toEntity(entry);
        final T savedEntity = repository.save(entity);
        return mapper.toEntry(savedEntity);
    }

    public List<R> createAll(@NonNull final List<R> entries) throws BadRequestException {

        if (CollectionUtils.isEmpty(entries)) {
            throw new BadRequestException("No data found.");
        }
        final List<T> entities = mapper.toEntities(entries);
        final List<T> savedEntities = repository.saveAll(entities);
        return mapper.toEntries(savedEntities);
    }

    public R read(@NonNull final String id) throws BadRequestException {

        final Optional<T> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new BadRequestException("No data found.");
        }
        final T entity = entityOptional.get();
        return mapper.toEntry(entity);
    }

    public List<R> readAll(@NonNull final List<String> ids) throws BadRequestException {

        if (CollectionUtils.isEmpty(ids)) {
            throw new BadRequestException("No data found.");
        }

        if (ids.size() > MAX_PAGE_FETCH_LIMIT) {
            throw new BadRequestException("Fetch limit exceeded.");
        }

        final List<T> entities = repository.findAllById(ids);
        return mapper.toEntries(entities);
    }

    public R update(@NonNull final String id, @NonNull final R entry) throws BadRequestException {

        final Optional<T> entityOptional = repository.findById(id);
        if (entityOptional.isEmpty()) {
            throw new BadRequestException("No data found.");
        }
        final T entity = entityOptional.get();
        final T updateEntity = mapper.toEntity(entry);
        final T updatedRequestEntity = mapper.toUpdate(updateEntity, entity);
        final T updatedEntity = repository.save(updatedRequestEntity);
        return mapper.toEntry(updatedEntity);
    }
}